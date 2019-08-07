package com.moesif.filter

import com.moesif.api.models.EventModel
import play.api.mvc.{RequestHeader, Result}

// Moesif provides the most value when we can identify users. You may also want to specify metadata, mask certain data,
// or prevent tracking of certain requests entirely. This is possible with the hooks below.
// To change the behavior of one of these hooks,
// 1.create a class that extends MoesifAdvancedFilterConfiguration and override methods as necessary
// 2. Once step 1 is done, create a object of that class(instantiate the class) and set MoesifAdvancedFilterConfiguration.set(config)
trait MoesifAdvancedFilterConfiguration {

   // identifyUser is a function that takes req and res as arguments and returns a userId.
  // This helps us attribute requests to unique users.
  def identifyUser(request: RequestHeader, result: Result): Option[String] = None

  // identifyCompany is a function that takes req and res as arguments and returns a companyId.
  // This helps us attribute requests to unique users.
  def identifyCompany(request: RequestHeader, result: Result): Option[String] = None

  // getMetadata is a function that takes a req and res and returns an object that allows you to
  // add custom metadata that will be associated with the req. The metadata must be a simple scala Map
  // For example, you may want to save a VM instance_id, a trace_id, or a tenant_id with the request.
  def getMetadata(request: RequestHeader, result: Result): Map[String, String] = Map.empty


  // From requestHeader and result, return session token
  def sessionToken(request: RequestHeader, result: Result): Option[String] = request.headers.headers.toMap.get("Authorization")

  // moesifEventModel is what gets sent to Moesif. mask contents as desired
  def maskContent(moesifEventModel: EventModel): EventModel = moesifEventModel

  //skip is a function that takes a req and res arguments and returns true if the event should be skipped (i.e. not logged)
  // The default is shown below and skips requests to the root path "/".
  def skip(request: RequestHeader, result: Result): Boolean = request.path ==  "/"

}


object MoesifAdvancedFilterConfiguration {
  private val _defaultConfig = new MoesifAdvancedFilterConfiguration {}
  private var _config: Option[MoesifAdvancedFilterConfiguration] =  None

  def setConfig(config: MoesifAdvancedFilterConfiguration): Unit = _config = Some(config)

  def getConfig() = _config

  def getDefaultConfig() = _defaultConfig
}