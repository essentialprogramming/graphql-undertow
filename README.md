# Undertow httpexchange application

Example of Postman endpoints:
* Get all articles that contain given tags:
```graphql endpoint
query articlesByTag($tags: [String]){
   allByTag(filter: $tags ){
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  title
  tags
  content
 
GraphQL variables:
{
	"tags": ["JVM","Arhitecture"]
}
```
* Get all articles from a specific author:
```graphql endpoint
query articlesByAuthor($firstName: String, $lastName: String){
   allByAuthor(firstName: $firstName, lastName: $lastName){
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  title
  tags
  content
  author{
      firstName
      lastName
      articles
      contactLinks
  }
  creationDate
  lastModified
  readingTime
  image
  comment {
      text
      commentAuthor
  }
}

GraphQL variables:
{
	"firstName": "Justin",
	"lastName": "Albano"
}
```
* View all articles:
```graphql endpoint
query articles{
   allArticles{
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  title
  tags
  content
  author{
      firstName
      lastName
      articles
      contactLinks
  }
  creationDate
  lastModified
  readingTime
  image
  comment {
      text
      commentAuthor
  }
}
```
* Create new article:
```graphql endpoint
mutation createArticle($article: ArticleInput, $author:String){
   createArticle(article: $article, author: $author){
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  id
  title
  tags
  content
  author{
     firstName
      lastName
      articles
      contactLinks
  }
  creationDate
  readingTime
  image
  
}

GraphQL variables:
{
	"article": {
		"title": "Article90",
		"tags": ["Java"],
		"content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
		"creationDate": "2019-12-20",
		"readingTime": 3,
		"image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg"
	},
	"author": "Justin"
}
```