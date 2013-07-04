// Do not edit this file. It is auto-generated from org.sireum.amandroid.module.OfaSCfg
// by org.sireum.pipeline.gen.ModuleGenerator

package org.sireum.amandroid.module

import org.sireum.util._
import org.sireum.pipeline._
import java.lang.String
import org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables
import org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp
import org.sireum.amandroid.module.AndroidInterProcedural.AndroidInterAnalysisResult
import org.sireum.amandroid.module.AndroidIntraProcedural.AndroidIntraAnalysisResult
import org.sireum.pilar.symbol.ProcedureSymbolTable
import org.sireum.pilar.symbol.SymbolTable
import scala.Option
import scala.Tuple2
import scala.collection.Seq
import scala.collection.mutable.Map

object OfaSCfgModule extends PipelineModule {
  def title = "System Control Flow Graph with OFA Builder"
  def origin = classOf[OfaSCfg]

  val globalOFAsCfgKey = "Global.OFAsCfg"
  val globalAppInfoKey = "Global.appInfo"
  val globalProcedureSymbolTablesKey = "Global.procedureSymbolTables"
  val globalAndroidCacheKey = "Global.androidCache"
  val globalCfgsKey = "Global.cfgs"
  val globalAndroidLibInfoTablesKey = "Global.androidLibInfoTables"
  val OFAsCfgKey = "OfaSCfg.OFAsCfg"
  val globalRdasKey = "Global.rdas"
  val globalCCfgsKey = "Global.cCfgs"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    try {
      val module = Class.forName("org.sireum.amandroid.module.OfaSCfgModuleDef")
      val cons = module.getConstructors()(0)
      val params = Array[AnyRef](job, info)
      val inst = cons.newInstance(params : _*)
    } catch {
      case e : Throwable =>
        e.printStackTrace
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, e.getMessage);
    }
    return tags
  }

  override def initialize(job : PipelineJob) {
  }

  override def validPipeline(stage : PipelineStage, job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    val deps = ilist[PipelineModule]()
    deps.foreach(d =>
      if(stage.modules.contains(d)){
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "'" + this.title + "' depends on '" + d.title + "' yet both were found in stage '" + stage.title + "'"
        )
      }
    )
    return tags
  }

  def inputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    var _cfgs : scala.Option[AnyRef] = None
    var _cfgsKey : scala.Option[String] = None

    val keylistcfgs = List(OfaSCfgModule.globalCfgsKey)
    keylistcfgs.foreach(key => 
      if(job ? key) { 
        if(_cfgs.isEmpty) {
          _cfgs = Some(job(key))
          _cfgsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _cfgs.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cfgs' keys '" + _cfgsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cfgs match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cfgs'.  Expecting 'scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cfgs'")       
    }
    var _rdas : scala.Option[AnyRef] = None
    var _rdasKey : scala.Option[String] = None

    val keylistrdas = List(OfaSCfgModule.globalRdasKey)
    keylistrdas.foreach(key => 
      if(job ? key) { 
        if(_rdas.isEmpty) {
          _rdas = Some(job(key))
          _rdasKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _rdas.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'rdas' keys '" + _rdasKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _rdas match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'rdas'.  Expecting 'scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'rdas'")       
    }
    var _cCfgs : scala.Option[AnyRef] = None
    var _cCfgsKey : scala.Option[String] = None

    val keylistcCfgs = List(OfaSCfgModule.globalCCfgsKey)
    keylistcCfgs.foreach(key => 
      if(job ? key) { 
        if(_cCfgs.isEmpty) {
          _cCfgs = Some(job(key))
          _cCfgsKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _cCfgs.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'cCfgs' keys '" + _cCfgsKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _cCfgs match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'cCfgs'.  Expecting 'scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'cCfgs'")       
    }
    var _androidCache : scala.Option[AnyRef] = None
    var _androidCacheKey : scala.Option[String] = None

    val keylistandroidCache = List(OfaSCfgModule.globalAndroidCacheKey)
    keylistandroidCache.foreach(key => 
      if(job ? key) { 
        if(_androidCache.isEmpty) {
          _androidCache = Some(job(key))
          _androidCacheKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _androidCache.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'androidCache' keys '" + _androidCacheKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _androidCache match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'androidCache'.  Expecting 'scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'androidCache'")       
    }
    var _appInfo : scala.Option[AnyRef] = None
    var _appInfoKey : scala.Option[String] = None

    val keylistappInfo = List(OfaSCfgModule.globalAppInfoKey)
    keylistappInfo.foreach(key => 
      if(job ? key) { 
        if(_appInfo.isEmpty) {
          _appInfo = Some(job(key))
          _appInfoKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _appInfo.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'appInfo' keys '" + _appInfoKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _appInfo match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'appInfo'.  Expecting 'org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'appInfo'")       
    }
    var _androidLibInfoTables : scala.Option[AnyRef] = None
    var _androidLibInfoTablesKey : scala.Option[String] = None

    val keylistandroidLibInfoTables = List(OfaSCfgModule.globalAndroidLibInfoTablesKey)
    keylistandroidLibInfoTables.foreach(key => 
      if(job ? key) { 
        if(_androidLibInfoTables.isEmpty) {
          _androidLibInfoTables = Some(job(key))
          _androidLibInfoTablesKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _androidLibInfoTables.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'androidLibInfoTables' keys '" + _androidLibInfoTablesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _androidLibInfoTables match{
      case Some(x) =>
        if(!x.isInstanceOf[org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'androidLibInfoTables'.  Expecting 'org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'androidLibInfoTables'")       
    }
    var _procedureSymbolTables : scala.Option[AnyRef] = None
    var _procedureSymbolTablesKey : scala.Option[String] = None

    val keylistprocedureSymbolTables = List(OfaSCfgModule.globalProcedureSymbolTablesKey)
    keylistprocedureSymbolTables.foreach(key => 
      if(job ? key) { 
        if(_procedureSymbolTables.isEmpty) {
          _procedureSymbolTables = Some(job(key))
          _procedureSymbolTablesKey = Some(key)
        }
        if(!(job(key).asInstanceOf[AnyRef] eq _procedureSymbolTables.get)) {
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': 'procedureSymbolTables' keys '" + _procedureSymbolTablesKey.get + " and '" + key + "' point to different objects.")
        }
      }
    )

    _procedureSymbolTables match{
      case Some(x) =>
        if(!x.isInstanceOf[scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable]]){
          tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
            "Input error for '" + this.title + "': Wrong type found for 'procedureSymbolTables'.  Expecting 'scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable]' but found '" + x.getClass.toString + "'")
        }
      case None =>
        tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
          "Input error for '" + this.title + "': No value found for 'procedureSymbolTables'")       
    }
    return tags
  }

  def outputDefined (job : PipelineJob) : MBuffer[Tag] = {
    val tags = marrayEmpty[Tag]
    if(!(job ? OfaSCfgModule.OFAsCfgKey) && !(job ? OfaSCfgModule.globalOFAsCfgKey)) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker,
        "Output error for '" + this.title + "': No entry found for 'OFAsCfg'. Expecting (OfaSCfgModule.OFAsCfgKey or OfaSCfgModule.globalOFAsCfgKey)") 
    }

    if(job ? OfaSCfgModule.OFAsCfgKey && !job(OfaSCfgModule.OFAsCfgKey).isInstanceOf[scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for OfaSCfgModule.OFAsCfgKey.  Expecting 'scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]' but found '" + 
        job(OfaSCfgModule.OFAsCfgKey).getClass.toString + "'")
    } 

    if(job ? OfaSCfgModule.globalOFAsCfgKey && !job(OfaSCfgModule.globalOFAsCfgKey).isInstanceOf[scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]]) {
      tags += PipelineUtil.genTag(PipelineUtil.ErrorMarker, 
        "Output error for '" + this.title + "': Wrong type found for OfaSCfgModule.globalOFAsCfgKey.  Expecting 'scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]' but found '" + 
        job(OfaSCfgModule.globalOFAsCfgKey).getClass.toString + "'")
    } 
    return tags
  }

  def getCfgs (options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]] = {
    if (options.contains(OfaSCfgModule.globalCfgsKey)) {
       return options(OfaSCfgModule.globalCfgsKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCfgs (options : MMap[Property.Key, Any], cfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]]) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalCfgsKey) = cfgs

    return options
  }

  def getRdas (options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]] = {
    if (options.contains(OfaSCfgModule.globalRdasKey)) {
       return options(OfaSCfgModule.globalRdasKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setRdas (options : MMap[Property.Key, Any], rdas : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalRdasKey) = rdas

    return options
  }

  def getCCfgs (options : scala.collection.Map[Property.Key, Any]) : scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]] = {
    if (options.contains(OfaSCfgModule.globalCCfgsKey)) {
       return options(OfaSCfgModule.globalCCfgsKey).asInstanceOf[scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setCCfgs (options : MMap[Property.Key, Any], cCfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]]) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalCCfgsKey) = cCfgs

    return options
  }

  def getAndroidCache (options : scala.collection.Map[Property.Key, Any]) : scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]] = {
    if (options.contains(OfaSCfgModule.globalAndroidCacheKey)) {
       return options(OfaSCfgModule.globalAndroidCacheKey).asInstanceOf[scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setAndroidCache (options : MMap[Property.Key, Any], androidCache : scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]]) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalAndroidCacheKey) = androidCache

    return options
  }

  def getAppInfo (options : scala.collection.Map[Property.Key, Any]) : org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp = {
    if (options.contains(OfaSCfgModule.globalAppInfoKey)) {
       return options(OfaSCfgModule.globalAppInfoKey).asInstanceOf[org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setAppInfo (options : MMap[Property.Key, Any], appInfo : org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalAppInfoKey) = appInfo

    return options
  }

  def getAndroidLibInfoTables (options : scala.collection.Map[Property.Key, Any]) : org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables = {
    if (options.contains(OfaSCfgModule.globalAndroidLibInfoTablesKey)) {
       return options(OfaSCfgModule.globalAndroidLibInfoTablesKey).asInstanceOf[org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setAndroidLibInfoTables (options : MMap[Property.Key, Any], androidLibInfoTables : org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalAndroidLibInfoTablesKey) = androidLibInfoTables

    return options
  }

  def getProcedureSymbolTables (options : scala.collection.Map[Property.Key, Any]) : scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable] = {
    if (options.contains(OfaSCfgModule.globalProcedureSymbolTablesKey)) {
       return options(OfaSCfgModule.globalProcedureSymbolTablesKey).asInstanceOf[scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setProcedureSymbolTables (options : MMap[Property.Key, Any], procedureSymbolTables : scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable]) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalProcedureSymbolTablesKey) = procedureSymbolTables

    return options
  }

  def getOFAsCfg (options : scala.collection.Map[Property.Key, Any]) : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]] = {
    if (options.contains(OfaSCfgModule.globalOFAsCfgKey)) {
       return options(OfaSCfgModule.globalOFAsCfgKey).asInstanceOf[scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]]
    }
    if (options.contains(OfaSCfgModule.OFAsCfgKey)) {
       return options(OfaSCfgModule.OFAsCfgKey).asInstanceOf[scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]]
    }

    throw new Exception("Pipeline checker should guarantee we never reach here")
  }

  def setOFAsCfg (options : MMap[Property.Key, Any], OFAsCfg : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]) : MMap[Property.Key, Any] = {

    options(OfaSCfgModule.globalOFAsCfgKey) = OFAsCfg
    options(OFAsCfgKey) = OFAsCfg

    return options
  }

  object ConsumerView {
    implicit class OfaSCfgModuleConsumerView (val job : PropertyProvider) extends AnyVal {
      def cfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]] = OfaSCfgModule.getCfgs(job.propertyMap)
      def rdas : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]] = OfaSCfgModule.getRdas(job.propertyMap)
      def cCfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]] = OfaSCfgModule.getCCfgs(job.propertyMap)
      def androidCache : scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]] = OfaSCfgModule.getAndroidCache(job.propertyMap)
      def appInfo : org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp = OfaSCfgModule.getAppInfo(job.propertyMap)
      def androidLibInfoTables : org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables = OfaSCfgModule.getAndroidLibInfoTables(job.propertyMap)
      def procedureSymbolTables : scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable] = OfaSCfgModule.getProcedureSymbolTables(job.propertyMap)
      def OFAsCfg : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]] = OfaSCfgModule.getOFAsCfg(job.propertyMap)
    }
  }

  object ProducerView {
    implicit class OfaSCfgModuleProducerView (val job : PropertyProvider) extends AnyVal {

      def cfgs_=(cfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]]) { OfaSCfgModule.setCfgs(job.propertyMap, cfgs) }
      def cfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]] = OfaSCfgModule.getCfgs(job.propertyMap)

      def rdas_=(rdas : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]]) { OfaSCfgModule.setRdas(job.propertyMap, rdas) }
      def rdas : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]] = OfaSCfgModule.getRdas(job.propertyMap)

      def cCfgs_=(cCfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]]) { OfaSCfgModule.setCCfgs(job.propertyMap, cCfgs) }
      def cCfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]] = OfaSCfgModule.getCCfgs(job.propertyMap)

      def androidCache_=(androidCache : scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]]) { OfaSCfgModule.setAndroidCache(job.propertyMap, androidCache) }
      def androidCache : scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]] = OfaSCfgModule.getAndroidCache(job.propertyMap)

      def appInfo_=(appInfo : org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp) { OfaSCfgModule.setAppInfo(job.propertyMap, appInfo) }
      def appInfo : org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp = OfaSCfgModule.getAppInfo(job.propertyMap)

      def androidLibInfoTables_=(androidLibInfoTables : org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables) { OfaSCfgModule.setAndroidLibInfoTables(job.propertyMap, androidLibInfoTables) }
      def androidLibInfoTables : org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables = OfaSCfgModule.getAndroidLibInfoTables(job.propertyMap)

      def procedureSymbolTables_=(procedureSymbolTables : scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable]) { OfaSCfgModule.setProcedureSymbolTables(job.propertyMap, procedureSymbolTables) }
      def procedureSymbolTables : scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable] = OfaSCfgModule.getProcedureSymbolTables(job.propertyMap)

      def OFAsCfg_=(OFAsCfg : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]) { OfaSCfgModule.setOFAsCfg(job.propertyMap, OFAsCfg) }
      def OFAsCfg : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]] = OfaSCfgModule.getOFAsCfg(job.propertyMap)
    }
  }
}

trait OfaSCfgModule {
  def job : PipelineJob

  def cfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.ControlFlowGraph[java.lang.String]] = OfaSCfgModule.getCfgs(job.propertyMap)

  def rdas : scala.collection.mutable.Map[java.lang.String, org.sireum.alir.MonotoneDataFlowAnalysisResult[scala.Tuple2[org.sireum.alir.Slot, org.sireum.alir.DefDesc]]] = OfaSCfgModule.getRdas(job.propertyMap)

  def cCfgs : scala.collection.mutable.Map[java.lang.String, org.sireum.amandroid.scfg.CompressedControlFlowGraph[java.lang.String]] = OfaSCfgModule.getCCfgs(job.propertyMap)

  def androidCache : scala.Option[org.sireum.amandroid.cache.AndroidCacheFile[java.lang.String]] = OfaSCfgModule.getAndroidCache(job.propertyMap)

  def appInfo : org.sireum.amandroid.androidObjectFlowAnalysis.PrepareApp = OfaSCfgModule.getAppInfo(job.propertyMap)

  def androidLibInfoTables : org.sireum.amandroid.AndroidSymbolResolver.AndroidLibInfoTables = OfaSCfgModule.getAndroidLibInfoTables(job.propertyMap)

  def procedureSymbolTables : scala.collection.Seq[org.sireum.pilar.symbol.ProcedureSymbolTable] = OfaSCfgModule.getProcedureSymbolTables(job.propertyMap)


  def OFAsCfg_=(OFAsCfg : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]]) { OfaSCfgModule.setOFAsCfg(job.propertyMap, OFAsCfg) }
  def OFAsCfg : scala.Tuple2[org.sireum.amandroid.androidObjectFlowAnalysis.AndroidObjectFlowGraph[org.sireum.amandroid.objectFlowAnalysis.OfaNode, org.sireum.amandroid.androidObjectFlowAnalysis.AndroidValueSet], org.sireum.amandroid.scfg.SystemControlFlowGraph[java.lang.String]] = OfaSCfgModule.getOFAsCfg(job.propertyMap)
}