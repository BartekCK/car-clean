import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { AuthContext } from '../context';

export const PrivateRoute = ({ component: Component, roles, ...rest }) => {
  const { authState } = React.useContext(AuthContext);

  return (
    <Route
      {...rest}
      render={(props) => {
        if (
          authState.isAuthenticated &&
          authState.roles &&
          roles.some((r) => authState.roles.includes(r))
        )
          return <Component {...props} />;
        else return <Redirect to='/zaloguj' />;
      }}
    />
  );
};
