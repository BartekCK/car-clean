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
import { PrivateRoute } from './services/privateRoutes';
import { OrderHistory } from './pages/OrdersHistory';
import { Payment } from './pages/Payment';
import { PaymentSuccess } from './containers/Payments/PaymentSuccess';

const App = (props) => {
  return (
    <AuthContextProvider>
      <Router>
        <AppNav />
        <Switch>
          <Route exact path='/' component={Home} />
          <Route exact path='/sklep' component={Shop} />
          <Route exact path='/kontakt' component={Contact} />
          <Route exact path='/opinie' component={Opinions} />
          <PrivateRoute
            roles={['ROLE_USER', 'ROLE_EMPLOYEE']}
            exact
            path='/payments/:type/:id'
            component={Payment}
          />
          <PrivateRoute
            roles={['ROLE_USER', 'ROLE_EMPLOYEE']}
            exact
            path='/koszyk'
            component={Basket}
          />
          <PrivateRoute
            roles={['ROLE_USER', 'ROLE_EMPLOYEE']}
            exact
            path='/zamowienia'
            component={OrderHistory}
          />
          <Route exact path='/oferta' component={Offer} />
          <PrivateRoute
            roles={['ROLE_USER', 'ROLE_EMPLOYEE']}
            exact
            path='/oferta/:id'
            component={ChosenOffer}
          />
          <Route exact path='/zaloguj' component={SignIn} />
          <Route exact path='/zarejestruj' component={SignUp} />
          <PrivateRoute
            roles={['ROLE_USER', 'ROLE_EMPLOYEE']}
            exact
            path='/pojazdy'
            component={UserCar}
          />
          <PrivateRoute
            roles={['ROLE_USER', 'ROLE_EMPLOYEE']}
            exact
            path='/historia'
            component={UserService}
          />
          <PrivateRoute
            roles={['ROLE_EMPLOYEE']}
            exact
            path='/serwisy'
            component={EmployeeService}
          />
          <Route exact path='/pay/accept' component={PaymentSuccess}/>
          <Route component={NotFound} />
        </Switch>
      </Router>
    </AuthContextProvider>
  );
};
export default App;
