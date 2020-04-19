import React from 'react';
import axios from 'axios';

export const AuthContext = React.createContext();

export const tempRoles = ['USER', 'EMPLOYEE'];

export class AuthContextProvider extends React.Component {
  state = {
    user: null,
    isAuthenticated: false,
    token: '',
    roles: [],
    error: null,
  };

  componentDidMount = async () => {
    const token = localStorage.getItem('@token');
    if (token) {
      //POST TO API TO GET USER
      const user = (
        await axios.get('https://jsonplaceholder.typicode.com/users/1')
      ).data;
      user.roles = tempRoles;
      if (user) {
        await this.setState({
          user,
          isAuthenticated: true,
          error: false,
          token,
          roles: user.roles,
        });
      }
    }
    //IF PROBLEM TO GET USER CLEAR TOKEN !
  };

  login = async (credentials) => {
    //POST TO API CREDENTIALS FOR LOGIN IF GOOD
    localStorage.setItem('@token', `${tempRoles[0]},${tempRoles[1]}`);
    const token = localStorage.getItem('@token'); //TEMP
    //POST TO API TO GET USER INFO
    const user = (
      await axios.get('https://jsonplaceholder.typicode.com/users/1')
    ).data;
    user.roles = tempRoles;
    if (user && token) {
      await this.setState({
        user,
        isAuthenticated: true,
        error: false,
        token,
        roles: user.roles,
      });
    }

    //IF WRONG
    // this.setState({ error: true });
  };

  logout = () => {
    localStorage.removeItem('@token');
    this.setState({
      user: null,
      isAuthenticated: false,
      token: '',
      roles: [],
      error: null,
    });
  };

  getToken = () => {
    const token = localStorage.getItem('@token');
    //CHECK TOKEN BY POST TO API AND THEN GIVE IT IN IF DOWN
    if (token && token.length > 3) {
      // this.setState({ token: token });
      return token;
    }
    return null;
  };

  getRole = async () => {
    const token = this.getToken();
    if (token) {
      //POST TOKEN TO API TO RETURN roles DOWN
      const roles = tempRoles;
      return roles;
    }
    return this.state.roles;
  };

  resetError = async () => {
    await this.setState({ error: null });
  };

  render() {
    const { error, isAuthenticated, roles } = this.state;
    return (
      <AuthContext.Provider
        value={{
          login: this.login,
          logout: this.logout,
          getRole: this.getRole,
          resetError: this.resetError,
          isAuthenticated: isAuthenticated,
          error: error,
          roles: roles,
        }}
      >
        {this.props.children}
      </AuthContext.Provider>
    );
  }
}

export const AuthContextConsumer = AuthContext.Consumer;
