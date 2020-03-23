const Comment = ({ comments }) => {
return (
<ul>
<li>
<p> Comments: </p>
 </li>
<ul>

    {comments && comments.map(({ id, text, commentAuthor }) => (
    <li key={text}>
        {commentAuthor}: {text}
    </li>
    ))}

</ul>
</ul>
);
};

const Author = ({ author }) => {
return (
<ul>
    <li key={author.firstName}>
        First name: {author.firstName}
    </li>
</ul>
);
};

const Article = ({ title, author, comments }) => {
  return (
    <li>
      <p>{`${title} by ${author.firstName}`}</p>
      <Author author={author} />
      <Comment comments={comments} />
    </li>
  );
};

class App extends React.Component {
  state = {
    //loading: false,
    data: [],
  }

  componentDidMount() {
    this.fetchData()
  }

  fetchData = () => {
      fetch('/api/graph', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          query: `query articles{
                        articles{
                              title
                              tags
                              content
                              author{
                                  firstName
                                  lastName
                                  articles{
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
                  }`,
        }),
      })
       .then(result => result.json())
       .then(result => {
            this.setState({
                    data: result.data.articles,
            })
       })
  }

  render() {

      const { data } = this.state

      const result =  data.map(item => (
                            <Article  key={item.title} {...item} />
                      ))

      return  (
           <div>
                <ul>
                    {result}
                </ul>
           </div>
      )
    }
}

ReactDOM.render(<App />, document.getElementById('root'))