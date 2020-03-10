# GraphQL application

### 💎 GraphQL Schema

We have three data types: Article, Author, Comment and one input: ArticleInput. 

```graphql 
schema {
    query: Query
    mutation: Mutation
}

type Query {
    allByTag(filter: [String]): [Article]
    allByAuthor(firstName: String, lastName: String): [Article]
    allBetweenDates(startDate: String, endDate: String): [Article]
    allArticles: [Article]
    articleById(id: String): Article
    allByTitle(filter: String): [Article]
    hello(message:String): String
}

type Mutation {
    createArticle(article: ArticleInput): Article!
}

input ArticleInput {
    id: ID
    title: String
    tags: [String]
    content: String
    creationDate: String
    readingTime: Int
    image: String
}

type Article {
    id: ID!
    title: String!
    tags: [String]
    content: String
    author: Author
    creationDate: String
    lastModified: String
    readingTime: Int
    image: String
    comment: [Comment]
}

type Author {
    id: ID!
    firstName: String
    lastName: String
    articles(count: Int): [Article]
    contactLinks: [String]
}

type Comment {
    id: ID!
    text: String
    commentAuthor: String
}
```

### 🎬 GraphQL Initialization
The responsibility of this class is to initialize GraphQL. This is done by first parsing the above schema file and then the resolvers.

Call .build() and .makeExecutableSchema() to get a graphql-java GraphQLSchema.

```java
@Component
public class GraphInit {

    private GraphQLSchema buildSchema(ArticleRepository articleRepository, AuthorRepository authorRepository, CommentRepository commentRepository) throws IOException {

        return SchemaParser.newParser()
                .file("article.graphql")
                .resolvers(
                        new Query(articleRepository),
                        new Mutation(articleRepository),
                        new ArticleResolver(authorRepository, commentRepository),
                        new AuthorResolver(articleRepository)
                )
                .build()
                .makeExecutableSchema();
    }

    @Bean
    public GraphQL graphQL(ArticleRepository articleRepository, AuthorRepository authorRepository, CommentRepository commentRepository) throws IOException {

        GraphQLSchema graphQLSchema = buildSchema(articleRepository, authorRepository, commentRepository);

        return GraphQL.newGraphQL(graphQLSchema)
                .build();
    }

}
```

### 🔑 Resolvers and Data Classes
 For most scalar fields, a POJO with fields and/or getter methods is enough to describe the data to GraphQL. More complex fields (like looking up another object) often need more complex methods with state not provided by the GraphQL context. GraphQL Java Tools uses the concept of “Data Classes” and “Resolvers” to account for both of these situations.
 
 The ArticleResolver (resolver for the Article "Data Class") look something like this:
 
 ```java
public class ArticleResolver implements GraphQLResolver<Article> {

    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    public ArticleResolver(AuthorRepository authorRepository, CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    public CompletableFuture<Author> author(Article article) {
        return CompletableFuture.supplyAsync(() -> {
            return authorRepository.getById(article.getAuthor().getId());
        });
    }

    public List<Comment> comment(Article article) {
        return commentRepository.getComments(article);
    }


}
```

Since the Query/Mutation objects are root GraphQL objects, they does not have an associated data class. In those cases, any resolvers implementing GraphQLQueryResolver or GraphQLMutationResolver will be searched for methods that map to fields in their respective root types.

##### Define GraphQL Queries
```java
class Query implements GraphQLQueryResolver {

    private final ArticleRepository articleRepository;

    public Query(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> allArticles() {
        return articleRepository.allArticles()
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
    }

}
```

##### Define GraphQL Mutations
```java
class Mutation implements GraphQLMutationResolver {

    private final ArticleRepository articleRepository;

    public Mutation(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(ArticleInput article) throws IOException {
        return ArticleMapper.entityToGraphQL(articleRepository.saveArticle(article));
    }
}
```

### 💾 CRUD GraphQL APIs


-Get all articles that contain given tags:
- GraphQL
```graphql 
query articlesByTag($tags: [String]){
   allByTag(filter: $tags ){
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  title
  tags
  content
}
```

- GraphQL Variables
```graphql 
{
	"tags":["JVM","Architecture"]
}
```

- Response
```graphql 
{
    "data": {
        "allByTag": [
            {
                "title": "Causes and Avoidance of java.lang.VerifyError",
                "tags": [
                    "Java",
                    "JVM"
                ],
                "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it."
            },
            {
                "title": "Best Practices for REST API Error Handling",
                "tags": [
                    "Architecture",
                    "REST"
                ],
                "content": "REST is a stateless architecture in which clients can access and manipulate resources on a server."
            }
        ]
    }
}
```

-Ger all articles from a specific author

- GraphQL

```graphql 
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
      articles(count: 2){
          title
      }
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
- GraphQL Variable
```graphql 
{
	"firstName": "Justin",
	"lastName": "Albano"
}
```
- Response
```graphql 
{
    "data": {
        "allByAuthor": [
            {
                "title": "Best Practices for REST API Error Handling",
                "tags": [
                    "Architecture",
                    "REST"
                ],
                "content": "REST is a stateless architecture in which clients can access and manipulate resources on a server.",
                "author": {
                    "firstName": "Justin",
                    "lastName": "Albano",
                    "articles": [
                        {
                            "title": "Best Practices for REST API Error Handling"
                        },
                        {
                            "title": "Causes and Avoidance of java.lang.VerifyError"
                        }
                    ],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                },
                "creationDate": "2018-10-22",
                "lastModified": "2019-01-11",
                "readingTime": 3,
                "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                "comment": [
                    {
                        "text": "First comment",
                        "commentAuthor": "Ion Popescu"
                    }
                ]
            },
            {
                "title": "Causes and Avoidance of java.lang.VerifyError",
                "tags": [
                    "Java",
                    "JVM"
                ],
                "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
                "author": {
                    "firstName": "Justin",
                    "lastName": "Albano",
                    "articles": [
                        {
                            "title": "Best Practices for REST API Error Handling"
                        },
                        {
                            "title": "Causes and Avoidance of java.lang.VerifyError"
                        }
                    ],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                },
                "creationDate": "2019-01-12",
                "lastModified": "2019-11-18",
                "readingTime": 2,
                "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                "comment": [
                    {
                        "text": "Second comment",
                        "commentAuthor": "Gigel"
                    }
                ]
            }
        ]
    }
}
```
-Get all articles betweend two dates
- GraphQL
```graphql 
query articlesByDate($startDate: String, $endDate: String){
   allBetweenDates(startDate: $startDate, endDate: $endDate){
       ...ArticleFragment
   }
}

```
- GraphQL Variables
```graphql 
{
	"startDate": "2019-01-01",
	"endDate":"2019-03-01"
}
```
- Result
```graphql 
{
    "data": {
        "allBetweenDates": [
            {
                "title": "Causes and Avoidance of java.lang.VerifyError",
                "tags": [
                    "Java",
                    "JVM"
                ],
                "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
                "author": {
                    "firstName": "Justin",
                    "lastName": "Albano",
                    "articles": [],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                },
                "creationDate": "2019-01-12",
                "lastModified": "2019-11-18",
                "readingTime": 2,
                "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                "comment": [
                    {
                        "text": "Second comment",
                        "commentAuthor": "Gigel"
                    }
                ]
            }
        ]
    }
}
```
-Get all articles
- GraphQL
```graphql 
query articles{
   allArticles{
       ...ArticleFragment
   }
}

```
- Result
```graphql 
{
    "data": {
        "allArticles": [
            {
                "title": "Best Practices for REST API Error Handling",
                "tags": [
                    "Architecture",
                    "REST"
                ],
                "content": "REST is a stateless architecture in which clients can access and manipulate resources on a server.",
                "author": {
                    "firstName": "Justin",
                    "lastName": "Albano",
                    "articles": [],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                },
                "creationDate": "2018-10-22",
                "lastModified": "2019-01-11",
                "readingTime": 3,
                "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                "comment": [
                    {
                        "text": "First comment",
                        "commentAuthor": "Ion Popescu"
                    }
                ]
            },
            {
                "title": "Causes and Avoidance of java.lang.VerifyError",
                "tags": [
                    "Java",
                    "JVM"
                ],
                "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
                "author": {
                    "firstName": "Justin",
                    "lastName": "Albano",
                    "articles": [],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                },
                "creationDate": "2019-01-12",
                "lastModified": "2019-11-18",
                "readingTime": 2,
                "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                "comment": [
                    {
                        "text": "Second comment",
                        "commentAuthor": "Gigel"
                    }
                ]
            },
            {
                "title": "A Guide to Java HashMap",
                "tags": [
                    "Java"
                ],
                "content": "Let's first look at what it means that HashMap is a map. A map is a key-value mapping, which means that every key is mapped to exactly one value and that we can use the key to retrieve the corresponding value from a map.",
                "author": {
                    "firstName": "Michael",
                    "lastName": "Krimgen",
                    "articles": [],
                    "contactLinks": [
                        "GitHub"
                    ]
                },
                "creationDate": "2019-05-22",
                "lastModified": "2019-11-18",
                "readingTime": 4,
                "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                "comment": [
                    {
                        "text": "There are no comments yet.",
                        "commentAuthor": "Admin"
                    }
                ]
            }
        ]
    }
}
```
-Get article by id
- GraphQL
```graphql 
query articlesById($id: String){
   articleById(id: $id){
       ...ArticleFragment
   }
}
```
- GraphQL Variables
```graphql 
{
	"id": "1"
}
```
- Result
```graphql 
{
    "data": {
        "articleById": {
            "title": "Best Practices for REST API Error Handling",
            "tags": [
                "Architecture",
                "REST"
            ],
            "content": "REST is a stateless architecture in which clients can access and manipulate resources on a server.",
            "author": {
                "firstName": "Justin",
                "articles": [],
                "contactLinks": [
                    "GitHub",
                    "Twitter"
                ]
            },
            "creationDate": "2018-10-22",
            "lastModified": "2019-01-11",
            "readingTime": 3,
            "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
            "comment": [
                {
                    "text": "First comment",
                    "commentAuthor": "Ion Popescu"
                }
            ]
        }
    }
}
```
-Get articles that contain a specific word in title
- GraphQL
```graphql 
query allArticlesByTitle($title: String){
   allByTitle(filter: $title){
       title
       tags
       content
   }
}
```
- GraphQL Variables
```graphql 
{
	"title": " HashMap"
}
```
- Result
```graphql 
{
    "data": {
        "allByTitle": [
            {
                "title": "A Guide to Java HashMap",
                "tags": [
                    "Java"
                ],
                "content": "Let's first look at what it means that HashMap is a map. A map is a key-value mapping, which means that every key is mapped to exactly one value and that we can use the key to retrieve the corresponding value from a map."
            }
        ]
    }
}
```
-Create new article
- GraphQL
```graphql 
mutation createArticle($article: ArticleInput,){
   createArticle(article: $article){
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  id
  title
  tags
  content
  creationDate
  readingTime
  image
  
}
```
- GraphQL Variables
```graphql 
{
	"article": {
		"title": "How to Read HTTP Headers in Spring REST Controllers",
		"tags": ["Rest", "Spring Web"],
		"content": "IIn this quick tutorial, we're going to look at how to access HTTP Headers in a Spring Rest Controller.",
		"creationDate": "2020-03-03",
		"readingTime": 6,
		"image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg"
	}
}
```
- Result
```graphql 
{
    "data": {
        "createArticle": {
            "id": "266000310",
            "title": "How to Read HTTP Headers in Spring REST Controllers",
            "tags": [
                "Rest",
                "Spring Web"
            ],
            "content": "IIn this quick tutorial, we're going to look at how to access HTTP Headers in a Spring Rest Controller.",
            "creationDate": "2020-03-06",
            "readingTime": 6,
            "image": "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg"
        }
    }
}
```