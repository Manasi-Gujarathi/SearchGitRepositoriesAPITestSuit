Feature: User can query search Git repositories API endpoint and can query based on a series of keywords, as well as can perform ordering and sorting of returned results.


Scenario Outline: User can search repositories where fork count is greater than given number and sort result by most stared repositories in ascending order.
Given User wants to search github repositories with search criteria where fork count is greater than "<Fork Count>" and records are sorted as per "<sort filter>" in "<Orderby>" order 
Then verify user gets successful response for the search criteria on forks
And verify that fork count is greater than "<Fork Count>"
And repositories are sorted with most stared and ordered in "<Orderby>" order as expected
Examples:
|Fork Count|sort filter|Orderby|
|1000|stars|asc|


Scenario Outline: User can search repositories with given keyword in their name and sort result by updated time in descending order.
Given User wants to search github repositories having given search keyword "<Keyword>"in name and records are sorted as per "<sort filter>" in "<Orderby>" order 
Then verify user gets successful response for the search query on keyword
And verify that search keyword "<Keyword>" is present in repositories name
And repositories are sorted with most stared and ordered in "<Orderby>" order as expected for search keyword
Examples:
|Keyword|sort filter|Orderby|
|testautomation|updated|desc|


Scenario Outline: User can search repositories with given username in login which are created after specified date.
Given User wants to search github repositories for specific user with name "<Username>" and created after date "<Date>" 
Then verify user gets successful response for the search query for username
And verify that given username "<Username>" is present as creator for the searched repositories
And verify that created date for repositories is after given date as "<Date>"
Examples:
|Username|Date|
|defunkt|2011-06-29T20:51:32Z|

 Scenario Outline: User can search repositories for multiple languages and sort result in descending order of their fork count.
Given User wants to search github repositories for language "<Lang1>" and  "<Lang2>" and records are sorted as per "<Sortfilter>" in "<OrderBy>" order
Then verify user gets successful response for the search query for multiple languages
And verify that given languages "<Lang1>" or "<Lang2>" are present in repositories
And repositories are sorted on fork count and ordered in "<Orderby>" order as expected for languages search
Examples:
|Lang1|Lang2|Sortfilter|OrderBy|
|javascript|java|forks|desc|

Scenario Outline: User can search repositories owned by GitHUB and which are archived and sort them in ascending order of their stars.
Given User wants to search github repositories owned by "<Name>" and archived property is "<Attribute>" and records are sorted as per "<Sortfilter>" in "<OrderBy>" order
Then verify user gets successful response for the search query for github owned repositories
And verify that "<Name>" is present in login
And verify that archival property is "<Attribute>" as expected
And github owned repositories are sorted on star count and ordered in "<OrderBy>" order as expected
Examples:
|Name|Attribute|Sortfilter|OrderBy|
|github|true|stars|asc|
