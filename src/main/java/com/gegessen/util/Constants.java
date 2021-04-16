package com.gegessen.util;

public interface Constants {

    // USERS CONSTANTS
    String FIND_USER_BY_EMAIL_MESSAGE = "FINDING USER with USERNAME: [%s].";
    String CREATE_USER_MESSAGE = "CREATING USER with USERNAME: [%s].";
    String UPDATE_USER_BY_EMAIL_MESSAGE = "UPDATING USER with USERNAME: [%s].";
    String DELETE_USER_BY_EMAIL_MESSAGE = "DELETING USER with USERNAME: [%s].";
    String GET_ALL_USERS_MESSAGE = "GETTING ALL USERS.";


    // RESTAURANT CONSTANTS
    String FIND_RESTAURANT_BY_ID_MESSAGE = "FINDING RESTAURANT with ID: [%s].";
    String CREATE_RESTAURANT_MESSAGE = "CREATING RESTAURANT with OWNER_USERNAME: [%s].";
    String UPDATE_RESTAURANT_BY_ID_MESSAGE = "UPDATING RESTAURANT with ID: [%s].";
    String DELETE_RESTAURANT_BY_ID_MESSAGE = "DELETING RESTAURANT with ID: [%s].";
    String GET_ALL_RESTAURANTS_MESSAGE = "GETTING ALL RESTAURANTS.";

    // PRODUCTS CONSTANTS
    String FIND_PRODUCT_BY_ID_MESSAGE = "FINDING PRODUCT with ID: [%s].";
    String CREATE_PRODUCT_MESSAGE = "CREATING PRODUCT with RESTAURANT ID: [%s].";
    String UPDATE_PRODUCT_BY_ID_MESSAGE = "UPDATING PRODUCT with ID: [%s].";
    String DELETE_PRODUCT_BY_ID_MESSAGE = "DELETING PRODUCT with ID: [%s].";
    String GET_ALL_PRODUCTS_MESSAGE = "GETTING ALL PRODUCTS.";

    // ORDER CONSTANTS
    String FIND_ORDER_BY_ID_MESSAGE = "FINDING ORDER with ID: [%s].";
    String CREATE_ORDER_MESSAGE = "CREATING ORDER WITH OWNER_EMAIL: [%s]";
    String UPDATE_ORDER_BY_ID_MESSAGE = "UPDATING ORDER with ID: [%s].";
    String DELETE_ORDER_BY_ID_MESSAGE = "DELETING ORDER with ID: [%s].";
    String GET_ALL_ORDERS_MESSAGE = "GETTING ALL ORDERS.";
    String uploadDirectory = System.getProperty("user.dir")+"/uploads";


    // SEARCH CONSTANTS
    String FIND_SEARCH_BY_ID_MESSAGE = "FINDING SEARCH with ID: [%s].";
    String CREATE_SEARCH_MESSAGE = "CREATING SEARCH with EMAIL: [%s].";
    String UPDATE_SEARCH_BY_ID_MESSAGE = "CREATING SEARCH with ID: [%s].";
    String DELETE_SEARCH_BY_ID_MESSAGE = "DELETING SEARCH with ID: [%s].";
    String GET_ALL_SEARCHES_MESSAGE = "GETTING ALL SEARCHES.";

    String FIND_BUCKET_BY_ID_MESSAGE = "FINDING BUCKET with ID: [%s].";
    String CREATE_BUCKET_MESSAGE = "CREATING BUCKET with PRODUCT ID: [%s].";
    String UPDATE_BUCKET_BY_ID_MESSAGE = "UPDATING BUCKET with PRODUCT ID: [%s].";
    String DELETE_BUCKET_BY_ID_MESSAGE = "DELETING BUCKET with ID: [%s].";
    String GET_ALL_BUCKETS_MESSAGE = "GETTING ALL BUCKETS.";

    // VERIFY CONSTANTS
    String VERIFY_TOKEN_MESSAGE = "VERIFYING TOKEN: [%s].";
    String SENDING_VERIFY_MAIL_MESSAGE = "SENDING TOKEN [%s] TO USER [%s] WITH EMAIL [%s]";
    String VERIFY_URL = "http://localhost:8080/config/api/v1/verify?token=";
    String RESET_PASSWORD_URL="http://localhost:8080/config/api/v1/changePasswordAfterReset?token=";
    String VERIFY_TOKEN_NOT_FOUND_MESSAGE="Cannot find VERIFICATION with TOKEN [%s]";
    String VERIFY_TOKEN_EXPIRED_EXCEPTION="VERIFICATION with TOKEN [%s] is expired";


    // TRIP EXCEPTIONS CONSTANTS
    String PRODUCT_NOT_FOUND_MESSAGE = "Cannot find PRODUCT with ID [%s].";

    // USER EXCEPTIONS CONSTANTS
    String USER_NOT_FOUND_MESSAGE="Cannot find USER with EMAIL [%s].";

    // CAR EXCEPTIONS CONSTANTS
    String BUCKET_NOT_FOUND_MESSAGE="Cannot find BUCKET WITH ID [%s].";

    //IMAGE EXCEPTIONS CONSTANTS
    String IMAGE_NOT_FOUND_EXCEPTION="Cannot find IMAGE with ID [%s].";

    String CREATE_FACEBOOK_USER_MESSAGE="Creating facebook USER with EMAIL [%s]";

    String CREATE_GUEST_USER_MESSAGE = "Creating guest user";


    String DATABASE_ERROR_MESSAGE = "Database error occurred.";
    String NOT_FOUND_MESSAGE = "Resource was not found";
    String BAD_REQUEST_MESSAGE = "Invalid input was given";
    String CONFLICT_CREATE_MESSAGE = "Conflict while creating entity";
    String EXISTING_RESOURCE_MESSAGE = "Resource already exists.";
    String CONFLICT_DELETE_MESSAGE = "Entity  not allowed to be deleted";
    String UNAUTHORIZED_MESSAGE= "Unauthorized request was given.";
    String CONFIRM_PASS_DOES_NOT_MATCH_MESSAGE="Confirm pass does not match user password.";
    String INVALID_CREDENTIALS_MESSAGE="Invalid credentials.";
    String USERNAME_NOT_EQUAL_MESSAGE="Username cannot be changed.";
    String BORN_DATE_NOT_EQAL_MESSAGE="Born Date cannot be changed.";
    String EMAIL_NOT_EQUAL_MESSAGE="Email cannot be changed.";
    String ROLE_NOT_EQUAL_MESSAGE="Role cannot be changed.";

    String EXISTING_EMAIL_MESSAGE="Email [%s] already exists.";
    String EXISTING_USERNAME_MESSAGE="Username [%s] already exists.";

    String PASSWORD_RESET_MESSAGE="Resetting password on user with email [%s]";

    String EMAIL_NOT_FOUND_MESSAGE = "Email [%s] was not found";

    String RESTAURANT_NOT_FOUND_MESSAGE="Restaurant with id [%s] was not found.";
    String ORDER_NOT_FOUND_MESSAGE="Order with id [%s] was not found.";

    String RESET_PASSWORD_SENT_MESSAGE="Sending new password TOKEN [%s] to USER [%s] with EMAIL [%s]";

    String USER_ALREADY_OWNING_RESTAURANT = "User with email [%s] already owns a restaurant";

}
