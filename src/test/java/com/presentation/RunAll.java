package com.presentation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {CompanyTest.class, PresentationTest.class,
        UserTest.class})
public class RunAll {
}
