import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {Home} from './pages/Home';
import {AppNav} from './containers/Navigation';
import {Shop} from './pages/Shop';
import {Basket} from './pages/Basket';
import {Offer} from './pages/Offer';
import {ChosenOffer} from './pages/ChosenOffer';
import {SignIn} from './pages/SignIn';
import {SignUp} from './pages/SignUp';
import {UserCar} from './pages/UserCar';
import {UserService} from "./pages/UserService";

class App extends React.Component {
  render() {
    return (
      <Router>
        <AppNav />
        <Switch>
          <Route exact path='/' component={Home} />
          <Route path='/sklep' component={Shop} />
          <Route path='/koszyk' component={Basket} />
          <Route exact path='/oferta' component={Offer} />
          {/*//PRIVATE ROUTE*/}
          <Route exact path='/oferta/:id' component={ChosenOffer} />
          <Route path='/zaloguj' component={SignIn} />
          <Route path='/zarejestruj' component={SignUp} />
          {/*//PRIVATE ROUTE*/}
          <Route exact path='/pojazdy' component={UserCar} />
          {/*//PRIVATE ROUTE*/}
          <Route exact path='/historia' component={UserService} />
        </Switch>
      </Router>
    );
  }
}
export default App;
