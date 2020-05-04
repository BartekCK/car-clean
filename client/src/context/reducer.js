export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';

export const authReducer = (state, action) => {
  switch (action.type) {
    case LOGIN:
      localStorage.setItem('@token', action.user.token);
      const temp = {
        isAuthenticated: true,
        token: action.user.token,
        roles: action.user.roles,
      };
      return {
        ...state,
        ...temp,
      };
    case LOGOUT:
      localStorage.clear();
      const temp2 = {
        isAuthenticated: false,
        token: '',
        roles: [],
      };
      return {
        ...state,
        ...temp2,
      };
    default:
      return state;
  }
};
