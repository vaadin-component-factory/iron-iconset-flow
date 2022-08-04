/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.componentfactory.addons.ironiconset;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag("iron-iconset")
@NpmPackage(value = "@polymer/iron-iconset", version = "3.0.1")
@JsModule("@polymer/iron-iconset/iron-iconset.js")
public class IronIconset extends Component {

    public IronIconset(String iconset, String setname, List<String> names, int size, int width) throws FileNotFoundException {
        this(setname, names, size, width);
        ClassResourceFactory factory = new ClassResourceFactory(iconset);
        getElement().setAttribute("src", factory.getStreamResource());
    }

    public IronIconset(AbstractStreamResource iconset, String setname, List<String> names, int size, int width) {
        this(setname, names, size, width);
        getElement().setAttribute("src", iconset);
    }

    protected IronIconset(String setname, List<String> names, int size, int width) {
        String namesString = names.stream().collect(Collectors.joining(" "));
        getElement().setAttribute("name", setname);
        getElement().setAttribute("icons", namesString);
        getElement().setAttribute("width", ""+width);
        getElement().setAttribute("size", ""+size);
    }

    public class ClassResourceFactory implements InputStreamFactory {
        private InputStream is = null;
        private String fileName;
        private File file;

        public ClassResourceFactory(String fileName) throws FileNotFoundException {
            Objects.requireNonNull(fileName, "The fileName can't be null");
            this.fileName = fileName;

            URL resource = this.getClass().getClassLoader().getResource("./META-INF/resources/" + fileName);
            if(resource == null) resource = this.getClass().getClassLoader().getResource(fileName);
            if (resource != null) {
                file = new File(resource.getFile());
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException e) { }
            } else {
                throw new FileNotFoundException(fileName);
            }
        }

        public StreamResource getStreamResource() {
            if (is == null) {
                return null;
            }
            StreamResource streamResource = new StreamResource(fileName, () -> is);
            return streamResource;
        }

        public File getFile() {
            return file;
        }

        @Override
        public InputStream createInputStream() {
            return is;
        }
    }
}