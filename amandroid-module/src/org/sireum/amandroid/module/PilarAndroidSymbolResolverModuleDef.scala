package org.sireum.amandroid.module

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.amandroid.AndroidSymbolResolver.AndroidSymbolTable
import org.sireum.amandroid.AndroidSymbolResolver.AndroidSymbolTableProducer
import org.sireum.amandroid.AndroidSymbolResolver.AndroidSymbolTableData
import org.sireum.amandroid.AndroidSymbolResolver.AndroidProcedureSymbolTableProducer
import org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables



object PilarAndroidSymbolResolverModuleDefObject{
    val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.symtab",
    None,
    "Pilar Symbol Resolution Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    ilist(MarkerTagKind.Problem, MarkerTagKind.Text))
    
    val WARNING_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.symtab",
    None,
    "Pilar Symbol Resolution Warning",
    MarkerTagSeverity.Warning,
    MarkerTagPriority.Normal,
    ilist(MarkerTagKind.Problem, MarkerTagKind.Text))
}


class PilarAndroidSymbolResolverModuleDef (val job : PipelineJob, info : PipelineJobModuleInfo) extends PilarAndroidSymbolResolverModule {
  val ms = this.models
  val par = this.parallel
  val fst = { _ : Unit => new ST }
  val result = 
    if (this.hasExistingAndroidLibInfoTables.isDefined) {
      require(this.hasExistingAndroidLibInfoTables.isDefined)
      val eavmt = this.hasExistingAndroidLibInfoTables.get.asInstanceOf[AndroidLibInfoTables]
      AndroidSymbolTable(ms, fst, eavmt, par, shouldBuildLibInfoTables)
    }
    else AndroidSymbolTable(ms, fst, par, shouldBuildLibInfoTables)
    

  val st = result._1.asInstanceOf[ST]
  info.tags ++= st.tags

  if (st.hasErrors)
    info.hasError = true
    
  this.symbolTable_=(result._1)
  
  this.androidLibInfoTablesOpt=(result._2)
  
}

class ST extends SymbolTable with SymbolTableProducer {
    st =>

    import PilarAndroidSymbolResolverModuleDefObject.ERROR_TAG_TYPE
    import PilarAndroidSymbolResolverModuleDefObject.WARNING_TAG_TYPE
    
    val tables = SymbolTableData()
    val tags = marrayEmpty[LocationTag]
    var hasErrors = false
     
    def reportError(source : Option[FileResourceUri], line : Int,
                    column : Int, message : String) : Unit = {
      tags += Tag.toTag(source, line, column, message, ERROR_TAG_TYPE)
      hasErrors = true
    }

    def reportWarning(fileUri : Option[String], line : Int,
                      column : Int, message : String) : Unit =
      tags += Tag.toTag(fileUri, line, column, message, WARNING_TAG_TYPE)

    val pdMap = mmapEmpty[ResourceUri, PST]

    def globalVars = null
    def globalVar(globalUri : ResourceUri) = null

    def procedures = tables.procedureTable.keys

    def procedures(procedureUri : ResourceUri) = tables.procedureTable(procedureUri)

    def procedureSymbolTables = pdMap.values

    def procedureSymbolTable(procedureAbsUri : ResourceUri) : ProcedureSymbolTable =
      procedureSymbolTableProducer(procedureAbsUri)

    def procedureSymbolTableProducer(procedureAbsUri : ResourceUri) = {
      assert(tables.procedureAbsTable.contains(procedureAbsUri))
      pdMap.getOrElseUpdate(procedureAbsUri, new PST(procedureAbsUri))
    }

class PST(val procedureUri : ResourceUri)
      extends ProcedureSymbolTable with ProcedureSymbolTableProducer {
    val tables = ProcedureSymbolTableData()
    var nextLocTable : CMap[ResourceUri, ResourceUri] = null
    def symbolTable = st
    def symbolTableProducer = st
    def procedure = st.tables.procedureAbsTable(procedureUri)
    def typeVars : ISeq[ResourceUri] = tables.typeVarTable.keys.toList
    def params : ISeq[ResourceUri] = tables.params.toList
    def isParam(localUri : ResourceUri) = tables.params.contains(localUri)
    def locals : Iterable[ResourceUri] = tables.localVarTable.keys
    def nonParamLocals : Iterable[ResourceUri] = tables.localVarTable.keys.filterNot(isParam)
    def locations =
      tables.bodyTables match {
        case Some(bt) => procedure.body.asInstanceOf[ImplementedBody].locations
        case _        => ivectorEmpty
      }
    def typeVar(typeVarUri : ResourceUri) : NameDefinition =
      tables.typeVarTable(typeVarUri)
    def param(paramUri : ResourceUri) : ParamDecl =
      tables.localVarTable(paramUri).asInstanceOf[ParamDecl]
    def local(localUri : ResourceUri) : LocalVarDecl =
      tables.localVarTable(localUri).asInstanceOf[LocalVarDecl]
    def location(locationIndex : Int) = locations(locationIndex)
    def location(locationUri : ResourceUri) =
      tables.bodyTables.get.locationTable(locationUri)
    def catchClauses(locationIndex : Int) : Iterable[CatchClause] =
      tables.bodyTables.get.catchTable.getOrElse(locationIndex,
        Array.empty[CatchClause] : Iterable[CatchClause])
  }

  def toSymbolTable : SymbolTable = this
}