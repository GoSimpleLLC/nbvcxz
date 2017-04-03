package me.gosimple.nbvcxz.resources;

import org.junit.Test;

import static me.gosimple.nbvcxz.resources.CharacterCaseUtil.fractionOfStringUppercase;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CharacterCaseUtilTest {
    @Test
    public void fractionOfStringUppercaseDoNotFailOnNullTest()
    {
        assertThat(fractionOfStringUppercase(null), is(0.0d));
    }

    @Test
    public void fractionOfStringUppercaseTest()
    {
        assertThat(fractionOfStringUppercase("TEST"), is(1.0d));
        assertThat(fractionOfStringUppercase("test"), is(0.0d));
        assertThat(fractionOfStringUppercase("teST"), is(0.5d));

        // Assert that characters without uppercase form are ignored.
        assertThat(fractionOfStringUppercase("TEST 5!"), is(1.0d));
        assertThat(fractionOfStringUppercase("test 5!"), is(0.0d));
        assertThat(fractionOfStringUppercase("teST 5!"), is(0.5d));

        assertThat(fractionOfStringUppercase("HaMbUrGer"), is(0.4444444444444444d));
    }
}