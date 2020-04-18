import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Home } from './pages/Home';
import { AppNav } from './containers/Navigation';
import { Shop } from './pages/Shop';
import { Basket } from './pages/Basket';
import { Offer } from './pages/Offer';
import { ChosenOffer } from './pages/ChosenOffer';
import { SignIn } from './pages/SignIn';
import { SignUp } from './pages/SignUp';
import { Opinions } from './pages/Opinions';
import { Contact } from './pages/Contact';
import { UserCar } from './pages/UserCar';
import { UserService } from './pages/UserService';
import { EmployeeService } from './pages/EmployeeServices';
import { NotFound } from './pages/NotFound';
import { AuthContextProvider } from './context';
import {PrivateRoute} from "./services/privateRoutes";

class App extends React.Component {
  render() {
    return (
      <Router>
        <AuthContextProvider>
          <AppNav />
          <Switch>
            <Route exact path='/' component={Home} />
            <Route exact path='/sklep' component={Shop} />
            <Route exact path='/kontakt' component={Contact} />
            <Route exact path='/opinie' component={Opinions} />
            {/*//PRIVATE ROUTE*/}
            <PrivateRoute roles={['USER','EMPLOYEE']} exact path='/koszyk' component={Basket} />
            <Route exact path='/oferta' component={Offer} />
            {/*//PRIVATE ROUTE*/}
            <PrivateRoute roles={['USER','EMPLOYEE']} exact path='/oferta/:id' component={ChosenOffer} />
            <Route exact path='/zaloguj' component={SignIn} />
            <Route exact path='/zarejestruj' component={SignUp} />
            {/*//PRIVATE ROUTE*/}
            <PrivateRoute roles={['USER','EMPLOYEE']} exact path='/pojazdy' component={UserCar} />
            {/*//PRIVATE ROUTE*/}
            <PrivateRoute roles={['USER','EMPLOYEE']} exact path='/historia' component={UserService} />
            {/*//PRIVATE ROUTE*/}
            <PrivateRoute roles={['EMPLOYEE']} exact path='/serwisy' component={EmployeeService} />
            <Route component={NotFound} />
          </Switch>
        </AuthContextProvider>
      </Router>
    );
  }
}
export default App;
