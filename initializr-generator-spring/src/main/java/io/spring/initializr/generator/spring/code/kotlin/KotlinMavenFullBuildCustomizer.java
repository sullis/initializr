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

package io.spring.initializr.generator.spring.code.kotlin;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionReference;

/**
 * {@link BuildCustomizer} for Kotlin projects build with Maven when Kotlin is not
 * supported by the parent and a full configuration should be provided.
 *
 * @author Andy Wilkinson
 */
class KotlinMavenFullBuildCustomizer implements BuildCustomizer<MavenBuild> {

	private final KotlinProjectSettings settings;

	KotlinMavenFullBuildCustomizer(KotlinProjectSettings kotlinProjectSettings) {
		this.settings = kotlinProjectSettings;
	}

	@Override
	public void customize(MavenBuild build) {
		build.properties().version(KotlinMavenBuildCustomizer.KOTLIN_VERSION_PROPERTY, this.settings.getVersion());
		build.settings()
			.sourceDirectory("${project.basedir}/src/main/kotlin")
			.testSourceDirectory("${project.basedir}/src/test/kotlin");
		build.plugins().add("org.jetbrains.kotlin", "kotlin-maven-plugin", (kotlinMavenPlugin) -> {
			kotlinMavenPlugin
				.versionReference(VersionReference.ofProperty(KotlinMavenBuildCustomizer.KOTLIN_VERSION_PROPERTY));
			kotlinMavenPlugin.configuration((configuration) -> {
				configuration.configure("args",
						(args) -> this.settings.getCompilerArgs().forEach((arg) -> args.add("arg", arg)));
				configuration.configure("compilerPlugins",
						(compilerPlugins) -> compilerPlugins.add("plugin", "spring"));
				configuration.add("jvmTarget", this.settings.getJvmTarget());
			});
			kotlinMavenPlugin.execution("compile", (compile) -> compile.phase("compile").goal("compile"));
			kotlinMavenPlugin.execution("test-compile",
					(compile) -> compile.phase("test-compile").goal("test-compile"));
			kotlinMavenPlugin.dependency("org.jetbrains.kotlin", "kotlin-maven-allopen",
					VersionReference.ofProperty(KotlinMavenBuildCustomizer.KOTLIN_VERSION_PROPERTY));
		});

	}

}
