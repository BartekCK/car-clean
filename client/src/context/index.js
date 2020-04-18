import React from 'react';

const AuthContext = React.createContext();

const tempRoles = ['USER', 'EMPLOYEE'];

export class AuthContextProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user: null,
      isAuthenticated: false,
      token: '',
      roles: [],
      error: null,
    };
    const token = localStorage.getItem('@token');
    if (token) {
      //POST TO API TO GET USER
      const user = {
        //TEMP
        id: 1,
        username: 'Bartek',
        email: 'bkotarski1@gmail.com',
        roles: tempRoles,
      };
      if (user) {
        this.state = {
          user,
          isAuthenticated: true,
          error: false,
          token,
          roles: user.roles,
        };
      }
    }
    //IF PROBLEM TO GET USER CLEAR TOKEN !
  }

  login = (credentials) => {
    //POST TO API CREDENTIALS FOR LOGIN IF GOOD
    localStorage.setItem('@token', `${tempRoles[0]},${tempRoles[1]}`);
    const token = localStorage.getItem('@token'); //TEMP
    //POST TO API TO GET USER INFO
    const user = {
      //TEMP
      id: 1,
      username: 'Bartek',
      email: 'bkotarski1@gmail.com',
      roles: tempRoles,
    };
    if (user && token) {
      this.setState({
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

  getRole = () => {
    const token = this.getToken();
    if (token) {
      //POST TOKEN TO API TO RETURN roles DOWN
      const roles = tempRoles;
      return roles;
    }
    return null;
  };

  resetError = () => {
    this.setState({ error: null });
  };

  render() {
    const { error, isAuthenticated, roles } = this.state;
    return (
      <AuthContext.Provider
        value={{
          login: this.login,
          logout: this.logout,
          getToken: this.getToken,
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
