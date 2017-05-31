/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

/**
 * 
 * @author Brandao
 *
 */
public interface HttpStatus {

	static final int ACCEPTED = 202;
	
	static final int ALREADY_REPORTED = 208;
	
	static final int BAD_GATEWAY = 502;
	
	static final int BAD_REQUEST = 400;
	
	static final int BANDWIDTH_LIMIT_EXCEEDED =	509;
	
	static final int CHECKPOINT = 103;
	
	static final int CONFLICT = 409;
	
	static final int CONTINUE = 100;
	
	static final int CREATED = 201;
	
	static final int EXPECTATION_FAILED = 417;
	
	static final int FAILED_DEPENDENCY = 424;
	
	static final int FORBIDDEN = 403;
	
	static final int FOUND = 302;
	
	static final int GATEWAY_TIMEOUT = 504;
	
	static final int GONE = 410;
	
	static final int HTTP_VERSION_NOT_SUPPORTED = 505;
	
	static final int I_AM_A_TEAPOT = 418;
	
	static final int IM_USED = 226;
	
	static final int INSUFFICIENT_STORAGE =	507;
	
	static final int INTERNAL_SERVER_ERROR = 500;
	
	static final int LENGTH_REQUIRED = 411;
	
	static final int LOCKED = 423;
	
	static final int LOOP_DETECTED = 508;
	
	static final int METHOD_NOT_ALLOWED = 405;
	
	static final int MOVED_PERMANENTLY = 301;
	
	static final int MULTI_STATUS = 207;
	
	static final int MULTIPLE_CHOICES =	300;
	
	static final int NETWORK_AUTHENTICATION_REQUIRED = 511;
	
	static final int NO_CONTENT = 204;
	
	static final int NON_AUTHORITATIVE_INFORMATION = 203;
	
	static final int NOT_ACCEPTABLE = 406;
	
	static final int NOT_EXTENDED = 510;
	
	static final int NOT_FOUND = 404;
	
	static final int NOT_IMPLEMENTED = 501;
	
	static final int NOT_MODIFIED = 304;
	
	static final int OK = 200;
	
	static final int PARTIAL_CONTENT = 206;
	
	static final int PAYLOAD_TOO_LARGE = 413;
	
	static final int PAYMENT_REQUIRED = 402;
	
	static final int PERMANENT_REDIRECT = 308;
	
	static final int PRECONDITION_FAILED = 412;
	
	static final int PRECONDITION_REQUIRED = 428;
	
	static final int PROCESSING = 102;
	
	static final int PROXY_AUTHENTICATION_REQUIRED = 407;
	
	static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
	
	static final int REQUEST_TIMEOUT = 408;
	
	static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
	
	static final int RESET_CONTENT = 205;
	
	static final int SEE_OTHER = 303;
	
	static final int SERVICE_UNAVAILABLE = 503;
	
	static final int SWITCHING_PROTOCOLS = 101;
	
	static final int TEMPORARY_REDIRECT = 307;
	
	static final int TOO_MANY_REQUESTS = 429;
	
	static final int UNAUTHORIZED = 401;
	
	static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;
	
	static final int UNPROCESSABLE_ENTITY = 422;
	
	static final int UNSUPPORTED_MEDIA_TYPE = 415;
	
	static final int UPGRADE_REQUIRED = 426;
	
	static final int URI_TOO_LONG = 414;
	
	static final int VARIANT_ALSO_NEGOTIATES = 506;
	
}
