/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grpc.server.configuration.etcd.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Spencer Gibb
 */

@ConfigurationProperties("spring.cloud.etcd.core")
public class EtcdCoreProperties {
	@NotNull
    private List<String> urls=new ArrayList<>();
	private boolean enabled = true;

	public EtcdCoreProperties() {
	}

	public EtcdCoreProperties(List<String> urls, boolean enabled) {
		this.urls = urls;
		this.enabled = enabled;
	}

	//	public EtcdProperties(List<URI> uris, boolean enabled) {
//		this.uris = uris;
//		this.enabled = enabled;
//	}

//	public List<URI> getUris() {
//		return uris;
//	}
//
//	public void setUris(List<URI> uris) {
//		this.uris = uris;
//	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

    public List<URI> convertToURI(){
		List<URI> uris= new ArrayList<>();
		for(String url:this.urls){
			uris.add(URI.create(url));
		}
		return uris;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EtcdCoreProperties that = (EtcdCoreProperties) o;

		if (enabled != that.enabled) return false;
		return urls != null ? urls.equals(that.urls) : that.urls == null;

	}

	@Override
	public int hashCode() {
		int result = urls != null ? urls.hashCode() : 0;
		result = 31 * result + (enabled ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return String.format("EtcdProperties{urls=%s, enabled=%s}", urls, enabled);
	}
}
