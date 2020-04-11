import React from "react"
import "./App.css"
import "bootstrap/dist/css/bootstrap.min.css"
import {BrowserRouter as Router, Route, Switch} from "react-router-dom"
import {Home} from "./pages/Home"
import {AppNav} from "./containers/Navigation"
import {Shop} from "./pages/Shop";

class App extends React.Component {
  render() {
    return (
      <Router>
        <AppNav />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/sklep" component={Shop} />
        </Switch>
      </Router>
    )
  }
}
export default App
