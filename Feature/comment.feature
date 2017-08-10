Feature: In order to comment (after the audience member clicks on the
comment button), they will then have to go through ‘Sign-in’ or ‘Register with
new account’ journey

@signin_comment
Scenario: As a already Registered user I want to post comment
Given I am on bbc news page
When I go to comments 
And I Sign in to comment
And I go to comments 
Then I add a comment and post that comment
And I should see the success message

@comment
Scenario: As a already logged in user I want to post comment
Given I am on bbc news page
And I Sign in
When I go to comments 
Then I add a comment and post that comment
And I should see the success message

@register_comment
Scenario: As a new user I want to post comment
Given I am on bbc news page
When I go to comments 
And I Register to comment
And I go to comments 
And I Sign in to comment
Then I add a comment and post that comment
And I should see the success message