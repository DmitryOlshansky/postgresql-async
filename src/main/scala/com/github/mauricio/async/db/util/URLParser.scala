/*
 * Copyright 2013 Maurício Linhares
 *
 * Maurício Linhares licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.mauricio.async.db.util

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.postgresql.util.ParseURL
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService
import scala.collection.JavaConversions._
import java.net.URI

object URLParser {

  private val Username = "username"
  private val Password = "password"

  import Configuration.Default

  def parse(url: String,
            bossPool: ExecutorService = Default.bossPool,
            workerPool: ExecutorService = Default.workerPool,
            charset: Charset = Default.charset
             ): Configuration = {

    val properties = ParseURL.parseURL(url).toMap

    println("properties ===> " + properties)

    val port = properties(ParseURL.PGPORT).toInt

    new Configuration(
      username = properties.get( Username ).getOrElse(Default.username),
      password = properties.get( Password ),
      database = properties.get(ParseURL.PGDBNAME),
      host = properties(ParseURL.PGHOST),
      port = port,
      charset = charset,
      workerPool = workerPool,
      bossPool = bossPool
    )

  }

}