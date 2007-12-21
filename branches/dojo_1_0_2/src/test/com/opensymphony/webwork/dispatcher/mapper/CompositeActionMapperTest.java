/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.mapper;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class CompositeActionMapperTest extends WebWorkTestCase {

    public void testReadFromActionMapperWebWorkProperties1() throws Exception {
        try {
            Configuration.setConfiguration(new Configuration() {
                Map properties = new LinkedHashMap();
                public void setImpl(String name, Object value) throws IllegalArgumentException, UnsupportedOperationException {
                    properties.put(name, value);
                }
                public Object getImpl(String aName) throws IllegalArgumentException {
                    return properties.get(aName);
                }
                public Iterator listImpl() {
                    return properties.keySet().iterator();
                }
            });
            Configuration.set(WebWorkConstants.WEBWORK_COMPOSITE_ACTION_MAPPER+"5", "com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapperTest$ActionMapper2");
            Configuration.set(WebWorkConstants.WEBWORK_COMPOSITE_ACTION_MAPPER+"3", "com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapperTest$ActionMapper1");

            CompositeActionMapper actionMapper = new CompositeActionMapper();
            List actionMapperInfos = actionMapper.getActionMapperInfos();

            assertNotNull(actionMapperInfos);
            assertEquals(actionMapperInfos.size(), 2);
            assertEquals(((CompositeActionMapper.ActionMapperInfo)actionMapperInfos.get(0)).getActionMapper().getClass(), ActionMapper1.class);
            assertEquals(((CompositeActionMapper.ActionMapperInfo)actionMapperInfos.get(1)).getActionMapper().getClass(), ActionMapper2.class);

        }
        finally {
            Configuration.setConfiguration(null);
        }
    }


    public void testReadFromActionMapperWebWorkProperties2() throws Exception {
        try {
            Configuration.setConfiguration(new Configuration() {
                Map properties = new LinkedHashMap();
                public void setImpl(String name, Object value) throws IllegalArgumentException, UnsupportedOperationException {
                    properties.put(name, value);
                }
                public Object getImpl(String aName) throws IllegalArgumentException {
                    return properties.get(aName);
                }
                public Iterator listImpl() {
                    return properties.keySet().iterator();
                }
            });
            Configuration.set(WebWorkConstants.WEBWORK_COMPOSITE_ACTION_MAPPER+"1", "com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapperTest$ActionMapper1");
            Configuration.set(WebWorkConstants.WEBWORK_COMPOSITE_ACTION_MAPPER+"2", "com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapperTest$ActionMapper2");

            CompositeActionMapper actionMapper = new CompositeActionMapper();
            List actionMapperInfos = actionMapper.getActionMapperInfos();

            assertNotNull(actionMapperInfos);
            assertEquals(actionMapperInfos.size(), 2);
            assertEquals(((CompositeActionMapper.ActionMapperInfo)actionMapperInfos.get(0)).getActionMapper().getClass(), ActionMapper1.class);
            assertEquals(((CompositeActionMapper.ActionMapperInfo)actionMapperInfos.get(1)).getActionMapper().getClass(), ActionMapper2.class);

        }
        finally {
            Configuration.setConfiguration(null);
        }
    }


    public void testGetActionMapping1() throws Exception {
        CompositeActionMapper actionMapper = new CompositeActionMapper(
                new ActionMapper[] {
                        new ActionMapper1(),
                        new ActionMapper2()
                }
        );

        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("param", "1");
        ActionMapping mapping = actionMapper.getMapping(request);

        assertNotNull(mapping);
        assertEquals(mapping.getName(), "1");
    }

    public void testGetActionMapping2() throws Exception {
        CompositeActionMapper actionMapper = new CompositeActionMapper(
                new ActionMapper[] {
                        new ActionMapper1(),
                        new ActionMapper2()
                }
        );

        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("param", "2");
        ActionMapping mapping = actionMapper.getMapping(request);

        assertNotNull(mapping);
        assertEquals(mapping.getName(), "2");
    }

    public void testGetActionMapping3() throws Exception {
        CompositeActionMapper actionMapper = new CompositeActionMapper(
                new ActionMapper[] {
                        new ActionMapper1(),
                        new ActionMapper2()
                }
        );

        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("param", "3");
        ActionMapping mapping = actionMapper.getMapping(request);

        assertNull(mapping);
    }

    public void testGetUrl1() throws Exception {
        CompositeActionMapper actionMapper = new CompositeActionMapper(
                new ActionMapper[] {
                        new ActionMapper1(),
                        new ActionMapper2()
                }
        );

        ActionMapping mapping = new ActionMapping();
        mapping.setName("1");
        String uri = actionMapper.getUriFromActionMapping(mapping);

        assertNotNull(uri);
        assertEquals(uri, "http://test1.com/test1");
    }

    public void testGetUrl2() throws Exception {
        CompositeActionMapper actionMapper = new CompositeActionMapper(
                new ActionMapper[] {
                        new ActionMapper1(),
                        new ActionMapper2()
                }
        );

        ActionMapping mapping = new ActionMapping();
        mapping.setName("2");
        String uri = actionMapper.getUriFromActionMapping(mapping);

        assertNotNull(uri);
        assertEquals(uri, "http://test2.com/test2");
    }

    public void testGetUri3() throws Exception {
        CompositeActionMapper actionMapper = new CompositeActionMapper(
                new ActionMapper[] {
                        new ActionMapper1(),
                        new ActionMapper2()
                }
        );

        ActionMapping mapping = new ActionMapping();
        mapping.setName("3");
        String uri = actionMapper.getUriFromActionMapping(mapping);

        assertNull(uri);
    }



    public static class ActionMapper1 implements ActionMapper {
        public ActionMapping getMapping(HttpServletRequest request) {
            if ("1".equals(request.getAttribute("param"))) {
                ActionMapping mapping = new ActionMapping();
                mapping.setName("1");
                return mapping;
            }
            return null;
        }

        public String getUriFromActionMapping(ActionMapping mapping) {
            if (mapping.getName().equals("1")) {
                return "http://test1.com/test1";
            }
            return null;
        }
    }

    public static class ActionMapper2 implements ActionMapper {
        public ActionMapping getMapping(HttpServletRequest request) {
            if ("2".equals(request.getAttribute("param"))) {
                ActionMapping mapping = new ActionMapping();
                mapping.setName("2");
                return mapping;
            }
            return null;
        }

        public String getUriFromActionMapping(ActionMapping mapping) {
            if (mapping.getName().equals("2")) {
                return "http://test2.com/test2";
            }
            return null;
        }
    }

}
