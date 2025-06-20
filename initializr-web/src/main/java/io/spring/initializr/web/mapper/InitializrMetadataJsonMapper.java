/*
 * Copyright 2012 - present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.web.mapper;

import io.spring.initializr.metadata.InitializrMetadata;

/**
 * Generate a JSON representation of the metadata.
 *
 * @author Stephane Nicoll
 */
public interface InitializrMetadataJsonMapper {

	/**
	 * Write a json representation of the specified metadata.
	 * @param metadata the initializr metadata
	 * @param appUrl the app URL
	 * @return the JSON representation
	 */
	String write(InitializrMetadata metadata, String appUrl);

}
