/*
Copyright (c) 2013-2014 Fengguo Wei & Sankardas Roy, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/
package org.sireum.jawa.sjc

import org.sireum.util._

/**
 * This class providing all helper methods for signature e.g., Ljava/lang/Object;.equals:(Ljava/lang/Object;)Z
 * 
 * @param signature should be following form: Ljava/lang/Object;.equals:(Ljava/lang/Object;)Z
 * @author <a href="mailto:fgwei@k-state.edu">Fengguo Wei</a>
 */
case class Signature(signature: String) extends JavaKnowledge {
  
  require(signature.indexOf('.') > 0 &&
          signature.indexOf(':') > 0 &&
          signature.indexOf(':') > signature.indexOf('.'))
  
  /**
   * class signature part of this signature: Ljava/lang/Object;
   */
  def classSigPart: String = signature.substring(0, signature.indexOf("."))
  
  /**
   * method name part of this signature
   */
  def methodNamePart: String = signature.substring(signature.indexOf(".") + 1, signature.indexOf(":"))
  
  /**
   * param signature part of this signature
   */
  def paramSigPart: String = signature.substring(signature.indexOf(":") + 1)
  
  def genSignature(classSigPart: String, methodNamePart: String, paramSigPart: String): String = {
    (classSigPart + "." + methodNamePart + ":" + paramSigPart).trim
  }
  
  private class ParameterSignatureIterator extends Iterator[String] {
    private var index = 1;

    def hasNext(): Boolean = {
      return index < paramSigPart.length() && paramSigPart.charAt(index) != ')';
    }

    def next(): String = {
      if (!hasNext())
          throw new NoSuchElementException();
      val result = new StringBuilder();
      var done: Boolean = false;
      do {
        done = true;
        val ch = paramSigPart.charAt(index);
        ch match {
          case 'B' | 'C' | 'D' | 'F' | 'I' | 'J' | 'S' | 'Z' =>
            result.append(paramSigPart.charAt(index));
            index+=1;
          case 'L' =>
            val semi = paramSigPart.indexOf(';', index + 1);
            if (semi < 0)
              throw new IllegalStateException("Invalid method paramSig: " + paramSigPart);
            result.append(paramSigPart.substring(index, semi + 1));
            index = semi + 1;
          case '[' =>
            result.append('[');
            index+=1;
            done = false;
          case _ =>
            throw new IllegalStateException("Invalid method paramSig: " + paramSigPart);
        }
      } while (!done);

      return result.toString();
    }

    def remove() = {
        throw new UnsupportedOperationException();
    }
  }
  
  /**
   * Get the method return type signature.
   * 
   * @return the method return type signature
   */
  def getReturnTypeSignature(): String = {
    val endOfParams = signature.lastIndexOf(')')
    if (endOfParams < 0)
      throw new IllegalArgumentException("Bad method signature: " + signature);
    return signature.substring(endOfParams + 1)
  }
  
  /**
   * Get the method return type. 
   * 
   * @return the method return type signature
   */
  def getReturnType(): JawaType = formatSignatureToType(getReturnTypeSignature)
  
  /**
   * Get the method return type. 
   * 
   * @return the method return type signature
   */
  def getReturnObjectType(): Option[JawaType] = {
    if(isReturnObject){
      val retPart = getReturnTypeSignature
      Some(formatSignatureToType(retPart))
    } else None
  }
  
  def isReturnNonNomal(): Boolean = {
    val ret = getReturnTypeSignature()
    ret.startsWith("L") || ret.startsWith("[")
  }
  
  def isReturnObject(): Boolean = {
    val ret = getReturnTypeSignature()
    ret.startsWith("L")
  }
  
  def isReturnArray(): Boolean = {
    val ret = getReturnTypeSignature()
    ret.startsWith("[")
  }
  
  def getReturnArrayDimension(): Int = {
    val ret = getReturnTypeSignature()
    if(ret.startsWith("["))
    	ret.lastIndexOf('[') - ret.indexOf('[') + 1
    else 0
  }

  def getParameters(): IList[String] = {
    var count = 0
    val params: MList[String] = mlistEmpty
    val iterator = new ParameterSignatureIterator
    while(iterator.hasNext){
      val p = iterator.next()
      params.insert(count, p)
      count+=1
    }
    params.toList
  }
  
  def getParameterTypes(): List[JawaType] = {
    val params: MList[JawaType] = mlistEmpty
    val iterator = new ParameterSignatureIterator
    while(iterator.hasNext){
      val p = formatSignatureToType(iterator.next())
      params += p
    }
    params.toList
  }
  
  def getParameterNum(): Int = {
    var count = 0
    val iterator = new ParameterSignatureIterator
    while(iterator.hasNext){
      val p = iterator.next()
      count+=1
    }
    count
  }
  
  def getObjectParameters(): MMap[Int, JawaType] = {
    var count = 0
    val params: MMap[Int, JawaType] = mmapEmpty
    val iterator = new ParameterSignatureIterator
    while(iterator.hasNext){
      val p = iterator.next()
      if(p.startsWith("L") || p.startsWith("[")){
      	params(count) = formatSignatureToType(p)
      }
      count+=1
    }
    params
  }
  
  /**
	 * get class name from method signature. e.g. Ljava/lang/Object;.equals:(Ljava/lang/Object;)Z -> java.lang.Object
	 */
  def getClassName: String = getClassType.name
  
  /**
   * get class type from method signature. e.g. Ljava/lang/Object;.equals:(Ljava/lang/Object;)Z -> (java.lang.Object, 0)
   */
  def getClassType: ObjectType = {
    formatSignatureToType(classSigPart).asInstanceOf[ObjectType]
  }
  
  def getSubSignature: String = {
    this.signature.substring(this.signature.indexOf(";.") + 2)
  }
  
  override def toString: String = this.signature
}