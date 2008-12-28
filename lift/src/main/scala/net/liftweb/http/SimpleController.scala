package net.liftweb.http

/*
* Copyright 2007-2008 WorldWide Conferencing, LLC
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions
* and limitations under the License.
*/

import _root_.scala.collection.immutable.TreeMap
import _root_.javax.servlet.http.{HttpServlet, HttpServletRequest , HttpServletResponse, HttpSession}
import _root_.net.liftweb.util._

/**
 * The base trait of Controllers that handle pre-view requests
 */
trait SimpleController
{
  def request: Req
  def httpRequest: HttpServletRequest

  def param(name: String): Box[String] = {
    request.params.get(name) match {
      case None => Empty
      case Some(nl) => nl.take(1) match {
        case Nil => Empty
        case l => Full(l.head)
      }
    }
  }

  def post_? : Boolean = request.post_?

  def get(name: String): Box[String] =
    httpRequest.getSession.getAttribute(name) match {
      case null => Empty
      case n: String => Full(n)
      case _ => Empty
    }

  def set(name: String, value: String) {
    httpRequest.getSession.setAttribute(name, value)
  }

  def unset(name: String) {
    httpRequest.getSession.removeAttribute(name)
  }
}

