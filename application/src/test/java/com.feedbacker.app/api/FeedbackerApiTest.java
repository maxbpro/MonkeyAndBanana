package com.feedbacker.app.api;


//import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


//@RunWith(RobolectricTestRunner.class)
public class FeedbackerApiTest
{
    private final static String LOCAL_GALLERY_XML = "src/test/resources/gallery_all.xml";
    private final static String LOCAL_PHOTOS_XML = "src/test/resources/photos_content.xml";
    private final static String LOCAL_MENU_XML = "src/test/resources/menu.xml";
    private final static String LOCAL_CELEBRITIES_JSON = "src/test/resources/celebrities.js";
    private final static String LOCAL_EVENTS_JSON = "src/test/resources/soho_anonse.js";
    private final static String LOCAL_VIDEO_JSON = "src/test/resources/video.js";


    @Before
    public void setUp() throws Exception
    {


    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetGalleries() throws Exception
    {


    }


}