import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { AuthContext } from '../context';

export class PrivateRoute extends React.Component {
  static contextType = AuthContext;

  state = {
    apiRoles: [],
    loaded: false,
  };

  componentDidMount = async () => {
    const { getRole } = this.context;
    const temp = await getRole();
    await this.setState({ apiRoles: temp, loaded: true });
  };

  render() {
    const { component: Component, roles, ...rest } = this.props;
    const { apiRoles, loaded } = this.state;

    if (!loaded) return null;
    return (
      <Route
        {...rest}
        render={(props) => {
          if (apiRoles && roles.some((r) => apiRoles.includes(r)))
            return <Component {...props} />;
          else return <Redirect to='/zaloguj' />;
        }}
      />
    );
  }
}
