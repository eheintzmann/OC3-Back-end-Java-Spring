package com.openclassrooms.api.controller;

public class AuthentificationParams {

    public static String[][] badRequestWhenRegisterTestData() {
        // @formatter:off
        return new String[][]{

                { // Blank
                    """
                    """
                },
                { // Invalid
                    """
                    {
                    """
                },
                { // Empty body
                    """
                    {}
                    """
                },
                { // Existing User
                    """
                    {"email":"test@test.com","name":"test TEST","password":"test!31"}
                    """
                },
                // Email
                { // Invalid Email
                    """
                    {"email":"new","name":"test TEST","password":"test!31"}
                    """
                },
                { // Blank Email
                    """
                    {"email":"","name":"test TEST","password":"test!31"}
                    """
                },
                { // Null Email
                    """
                    {"email": null,"name":"test TEST","password":"test!31"}
                    """
                },
                { // Missing Email
                    """
                    {"name":"test TEST","password":"test!31"}
                    """
                },
                // Name
                { // Blank Name
                    """
                    {"email":"test@test.com","name":"","password":"test!31"}
                    """
                },
                { // Null Name
                    """
                    {"email":"test@test.com","name":null,"password":"test!31"}
                    """
                },
                { // Missing Name
                    """
                    {"email":"new@test.com","password":"test!31"}
                    """
                },
                // Password
                { // Blank password
                    """
                    {"email":"test@test.com","name":"test TEST","password":""}
                    """
                },
                { // Null password
                    """
                    {"email":"test@test.com","name":"test TEST","password":null}
                    """
                },
                { // Missing Password
                    """
                    {"email":"new@test.com","name":"test TEST"}
                    """
                }
        };
        // @formatter:on
    }

    public static String[][] unauthorizedWhenLoginTestData() {
        // @formatter:off
        return new String[][]{

                { // Empty body
                    """
                    {}
                    """
                },
                { // Non Existing User
                    """
                    {"email":"none@test.com","password":"test!31"}
                    """
                },
                // Login
                { // Invalid Login
                    """
                    {"email":"test","password":"test!31"}
                    """
                },
                { // Blank Login
                    """
                    {"email":"","password":"test!31"}
                    """
                },
                { // Null Login
                    """
                    {"email":null,"password":"test!31"}
                    """
                },
                { // Missing Login
                    """
                    {"password":"test!31"}
                    """
                },
                // Password
                { // Wrong Password
                    """
                    {"email":"test@test.com","password":"test31"}
                    """
                },
                { // Blank Password
                    """
                    {"email":"test@test.com","password":""}
                    """
                },
                { // Null Password
                    """
                    {"email":"test@test.com","password":null}
                    """
                },
                { // Missing Password
                    """
                    {"email":"new@test.com"}
                    """
                }
        };
        // @formatter:on
    }
}
