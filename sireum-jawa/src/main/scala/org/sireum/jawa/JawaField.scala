/*
Copyright (c) 2013-2014 Fengguo Wei & Sankardas Roy, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/
package org.sireum.jawa

import org.sireum.jawa.util.StringFormConverter

/**
 * This class is an amandroid representation of a pilar field. It can belong to JawaClass.
 * You can also construct it manually. 
 * 
 * @author <a href="mailto:fgwei@k-state.edu">Fengguo Wei</a>
 */
class JawaField {
  
  /**
   * name of the field. e.g. stackState
   */
  
	protected var name : String = null
	
	/**
	 * signature of the field. e.g. java.lang.Throwable.stackState
	 */
	
	protected var signature : String = null
	
	/**
	 * type of the field
	 */
	
	protected var typ : Type = null
	
	/**
	 * access flags of the field
	 */
	
	protected var accessFlags : Int = 0
	
	/**
	 * declaring class of this field
	 */
	
	protected var declaringClass : JawaClass = null
	
   /**
   * when you construct an amandroid field instance, call this init function first
   */
	
	def init(name : String, typ : Type, accessFlags : Int) : JawaField = {
	  this.accessFlags = accessFlags
	  if(isSignature(name)){
	    if(isStatic) this.signature = "@@" + name
	    else this.signature = name
	    this.name = StringFormConverter.getFieldNameFromFieldSignature(name)
	  } else {
	  	this.name = name
	  }
	  this.typ = typ
	  this
	}
	
	/**
   * when you construct an amandroid field instance, call this init function first
   */
	
	def init(name : String, typ : Type) : JawaField = init(name, typ, 0)
	
	/**
   * when you construct an amandroid field instance, please call this init function first
   */
	
	def init(name : String, accessFlags : Int) : JawaField = init(name, null, accessFlags)
	
	/**
   * when you construct an amandroid field instance, call this init function first
   */
	
	def init(name : String) : JawaField = init(name, null, 0)
	
	/**
	 * get this field's name
	 */
	
	def getName = this.name
	
	/**
	 * get typ of the field
	 */
	
	def getType = this.typ
	
	/**
	 * set field name
	 */
	
	def setName(name : String) = this.name = name
	
	/**
	 * set field type
	 */
	
	def setType(typ : Type) = this.typ = typ
	
	/**
	 * set field access flags
	 */
	
	def setAccessFlags(af : Int) = this.accessFlags = af
	
	/**
	 * set field access flags
	 */
	
	def setAccessFlags(str : String) = this.accessFlags = AccessFlag.getAccessFlags(str)
	
	/**
	 * get field access flags
	 */
	
	def getAccessFlags = this.accessFlags
	
	/**
	 * get field access flags in text form
	 */
	
	def getAccessFlagsStr = AccessFlag.toString(this.accessFlags)
	
	/**
	 * get declaring class
	 */
	
	def getDeclaringClass : JawaClass = {
	  if(!isDeclared) throw new RuntimeException("not declared: " + getName + " " + getType)
	  declaringClass
	}
	
	/**
	 * set declaring class of this field
	 */
	
	def setDeclaringClass(dr : JawaClass) ={
	  this.declaringClass = dr
	  if(this.signature == null) this.signature = generateSignature(this.declaringClass, this.name, isStatic)
	}
	
	/**
	 * clear declaring class of this field
	 */
	
	def clearDeclaringClass = this.declaringClass = null
	
	/**
	 * return true if the field is public
	 */
	
	def isPublic = AccessFlag.isPublic(this.accessFlags)
	
	/**
	 * return true if the field is protected
	 */
	
	def isProtected = AccessFlag.isProtected(this.accessFlags)
	
	/**
	 * return true if the field is private
	 */
	
	def isPrivate = AccessFlag.isPrivate(this.accessFlags)
	
	/**
	 * return true if the field is static
	 */
	
	def isStatic = AccessFlag.isStatic(this.accessFlags)
	
	/**
	 * return true if the field is final
	 */
	
	def isFinal = AccessFlag.isFinal(this.accessFlags)
	
	/**
	 * return true if the field is object type
	 */
	
	def isObject = !Center.isJavaPrimitiveType(typ)
	
	/**
	 * check if given string is field signature or not
	 */
	
	def isSignature(str : String) = StringFormConverter.isValidFieldSig(str)
	
	/**
	 * generate signature of this field
	 */
	
	def generateSignature(ar : JawaClass, name : String, isStatic : Boolean) : String = StringFormConverter.generateFieldSignature(ar.getName, name, isStatic)
	
	/**
	 * get signature of this field
	 */
	
	def getSignature = {
	  if(this.signature == null) this.signature = generateSignature(this.declaringClass, this.name, isStatic)
	  this.signature
	}
	
	/**
	 * this field is declared or not
	 */
	
	def isDeclared = declaringClass != null
	
	override def toString() : String = getSignature
	
	def printDetail = {
	  println("~~~~~~~~~~~~~JawaField~~~~~~~~~~~~~")
	  println("name: " + getName)
	  println("sig: " + getSignature)
	  println("type: " + getType)
	  println("accessFlags: " + AccessFlag.toString(getAccessFlags))
	  println("declaringClass: " + getDeclaringClass)
	  println("~~~~~~~~~~~~~~~~~~~~~~~~~~")
	}
	
}