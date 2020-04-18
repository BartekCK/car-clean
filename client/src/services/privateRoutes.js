import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { AuthContextConsumer } from '../context';

export const PrivateRoute = ({ component: Component, roles, ...rest }) => (
  <Route
    {...rest}
    render={(props) => (
      <AuthContextConsumer>
        {({ getRole }) => {
          const apiRoles = getRole();
          if (apiRoles && roles.some((r) => apiRoles.includes(r)))
            return <Component {...props} />;
          else return <Redirect to='/zaloguj' />;
        }}
      </AuthContextConsumer>
    )}
  />
);
