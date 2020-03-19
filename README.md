# GraphQL application

### 🌀 Build and run
Start the  application by running Server class in your IDE or by running :

`java - jar uber-undertow-httpexchange-1.0-SNAPSHOT.jar`

### 💎 GraphQL Schema

We have three data types: Article, Author, Comment and one input: ArticleInput. 

```graphql 
schema {
    query: Query
    mutation: Mutation
}

type Query {
       hello(message:String): String
       articleById(id: String): Article
       getArticles(filter: Filter):[Article]
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

input Filter {
    tags: [String]
    firstName: String
    lastName: String
    startDate: String
    endDate: String
    title: String
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

    public Article articleById(String id) {
           return ArticleMapper.entityToGraphQL(articleRepository.getById(id));
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
        return ArticleMapper.entityWithoutAuthorToGraphQL(articleRepository.saveArticle(article));
    }
}
```

### 💾  Query samples

The request URL is: `http://localhost:8080/api/graph`

-Get all articles that correspond to specific filters: 

- GraphQL
```graphql
query listAll{
    getArticles(filter: {
       title:"java", tags: ["Architecture", "JAVA"], firstName:"Justin", lastName:"Albano",
        startDate:"2019-01-01", endDate:"2019-03-01"
    })
   {
        title
        content
        tags
        author {
            firstName
            articles(count:2) {
            title
      }
      contactLinks
        }
       
    }
}
```

- Response
```json5
{
    "data": {
        "getArticles": [
            {
                "title": "Causes and Avoidance of java.lang.VerifyError",
                "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
                "tags": [
                    "Java",
                    "JVM"
                ],
                "author": {
                    "firstName": "Justin",
                    "articles": [
                        {
                            "title": "Causes and Avoidance of java.lang.VerifyError"
                        },
                        {
                            "title": "A Guide to Java HashMap"
                        }
                    ],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                }
            }
        ]
    }
}
```

-Get all articles:

- GraphQL
```graphql
query listAll{
    getArticles {
        title
        content
        tags
        author {
            firstName
            articles(count:1) {
            title
      }
      contactLinks
        }
       
    }
}
```
- Response
```json5
{
    "data": {
        "getArticles": [
            {
                "title": "Best Practices for REST API Error Handling",
                "content": "REST is a stateless architecture in which clients can access and manipulate resources on a server.",
                "tags": [
                    "Architecture",
                    "REST"
                ],
                "author": {
                    "firstName": "Michael",
                    "articles": [
                        {
                            "title": "Best Practices for REST API Error Handling"
                        }
                    ],
                    "contactLinks": [
                        "GitHub"
                    ]
                }
            },
            {
                "title": "Causes and Avoidance of java.lang.VerifyError",
                "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
                "tags": [
                    "Java",
                    "JVM"
                ],
                "author": {
                    "firstName": "Justin",
                    "articles": [
                        {
                            "title": "Causes and Avoidance of java.lang.VerifyError"
                        }
                    ],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                }
            },
            {
                "title": "A Guide to Java HashMap",
                "content": "Let's first look at what it means that HashMap is a map. A map is a key-value mapping, which means that every key is mapped to exactly one value and that we can use the key to retrieve the corresponding value from a map.",
                "tags": [
                    "Java"
                ],
                "author": {
                    "firstName": "Justin",
                    "articles": [
                        {
                            "title": "Causes and Avoidance of java.lang.VerifyError"
                        }
                    ],
                    "contactLinks": [
                        "GitHub",
                        "Twitter"
                    ]
                }
            }
        ]
    }
}
```

-Get article by id:

- GraphQL
```graphql
query articlesById($id: String){
   articleById(id: $id){
       ...ArticleFragment
   }
}

fragment ArticleFragment on Article {
  title 
  tags 
  content
  author{
      firstName
      articles(count:2) {
          title
      }
      contactLinks
  }
  creationDate
  lastModified
  readingTime
  image
  comments {
      text
      commentAuthor
  }
}
```
- GraphQL Variables
```json5
{
	"id": "2"
}
```

- Response
```json5
{
    "data": {
        "articleById": {
            "title": "Causes and Avoidance of java.lang.VerifyError",
            "tags": [
                "Java",
                "JVM"
            ],
            "content": "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
            "author": {
                "firstName": "Justin",
                "articles": [
                    {
                        "title": "Causes and Avoidance of java.lang.VerifyError"
                    },
                    {
                        "title": "A Guide to Java HashMap"
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
            "comments": [
                {
                    "text": "Second comment",
                    "commentAuthor": "Gigel"
                }
            ]
        }
    }
}
```
