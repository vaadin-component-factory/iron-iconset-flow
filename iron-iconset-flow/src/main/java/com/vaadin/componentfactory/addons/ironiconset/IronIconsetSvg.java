package com.vaadin.componentfactory.addons.ironiconset;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag("iron-iconset-svg")
@NpmPackage(value = "@polymer/iron-iconset-svg", version = "3.0.1")
@JsModule("@polymer/iron-iconset-svg/iron-iconset-svg.js")
public class IronIconsetSvg extends Component {
    public IronIconsetSvg(String iconset, String setname, int size) throws FileNotFoundException {
        this(setname, size);
        ClassResourceFactory factory = new ClassResourceFactory(iconset);
        getElement().setProperty("innerHTML", factory.getStreamResourceContent());
    }

    public IronIconsetSvg(AbstractStreamResource iconset, String setname, int size) throws FileNotFoundException {
        this(setname, size);
        ClassResourceFactory factory = new ClassResourceFactory(iconset.getName());
        getElement().setProperty("innerHTML", factory.getStreamResourceContent());
    }

    protected IronIconsetSvg(String setname, int size) {
        getElement().setAttribute("name", setname);
        getElement().setAttribute("size", ""+size);
    }


    public class ClassResourceFactory implements InputStreamFactory {
        private InputStream is = null;
        private String fileName;
        private File file;

        public ClassResourceFactory(String fileName) throws FileNotFoundException {
            Objects.requireNonNull(fileName, "The fileName can't be null");
            this.fileName = fileName;
            URL resource = this.getClass().getClassLoader()
                    .getResource(fileName);
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

        public String getStreamResourceContent() throws FileNotFoundException {
            Objects.requireNonNull(fileName, "The fileName can't be null");
            URL resource = this.getClass().getClassLoader().getResource(fileName);
            if (resource != null) {
                file = new File(resource.getFile());
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException e) { }

                String content;
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    try {
                        content = (String) bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
                    } catch (Throwable ex) {
                        try { bufferedReader.close(); } catch (Throwable var7) { }
                        throw ex;
                    }

                    bufferedReader.close();
                    return content;
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new FileNotFoundException(fileName);
            }

            return null;
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