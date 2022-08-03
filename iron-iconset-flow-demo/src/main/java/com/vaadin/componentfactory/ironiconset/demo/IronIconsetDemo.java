/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.componentfactory.ironiconset.demo;

import com.vaadin.componentfactory.addons.ironiconset.IronIconset;
import com.vaadin.componentfactory.addons.ironiconset.IronIconsetSvg;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.FileNotFoundException;
import java.util.Arrays;

@PageTitle("Addon DEMO")
@Route(value = "")
public class IronIconsetDemo extends Div {

    private TextField name;
    private Button sayHello;

    public IronIconsetDemo() throws FileNotFoundException {
        IronIconset iconWrapper = new IronIconset("my-icons.png", "myicons", Arrays.asList("location","place","starta","stopb","bus","car","train","walk"), 24, 96);
        IronIcon bus = new IronIcon("myicons","bus");
        add(iconWrapper, bus);

        IronIconsetSvg iconWrapper2 = new IronIconsetSvg("svgicons.svg", "myicons2", 24);
        IronIcon shape1 = new IronIcon("myicons2","shape");
        add(iconWrapper2, shape1);
    }

}
