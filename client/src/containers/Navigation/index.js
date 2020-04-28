import React from 'react';
import { Image, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import Logo from './../../resources/img/logo.png';
import User from './../../resources/img/user.png';
import { AuthContext } from '../../context';
import { Link } from 'react-router-dom';
import { LOGOUT } from '../../context/reducer';
import styled from 'styled-components';

const UserImage = () => <img alt='' width='40' height='40' src={User} />;

export const AppNav = (props) => {
  const { authState, dispatch } = React.useContext(AuthContext);

  return (
    <Navbar
      className='border-bottom border-dark'
      collapseOnSelect
      expand='lg'
      bg='dark'
      variant='dark'
    >
      <Link to='/'>
        <Navbar.Brand>
          <img
            alt=''
            src={Logo}
            width='100'
            height='40'
            className='d-inline-block align-top'
          />
        </Navbar.Brand>
      </Link>

      <Navbar.Toggle aria-controls='responsive-navbar-nav' />
      <Navbar.Collapse id='responsive-navbar-nav'>
        <Nav className='mr-auto'>
          <HLink to='/sklep'>Sklep</HLink>
          <HLink to='/oferta'>Oferta</HLink>
          <HLink to='/kontakt'>Kontakt</HLink>
          <HLink to='/opinie'>Opinie</HLink>

          {/*ONLY FOR EMPLOYESS*/}
          {authState.roles.includes('ROLE_EMPLOYEE') && (
            <HLink to='/serwisy'>Przegląd serwisów</HLink>
          )}
          {/*END*/}
        </Nav>

        <Nav className='mr-1'>
          {/*IF USER NO SIGN IN */}
          {!authState.isAuthenticated && (
            <>
              <HLink to='/zaloguj'>Zaloguj</HLink>
              <HLink to='/zarejestruj'>Zarejestruj</HLink>
            </>
          )}
          {/*END*/}

          {/*IF USER IS SIGN IN*/}
          {authState.isAuthenticated && (
            <NavDropdown
              as={Image}
              title={<UserImage />}
              id='dropdown-basic'
              drop='left'
            >
              <Vlink to='/koszyk'>Koszyk</Vlink>
              <NavDropdown.Divider />
              <Vlink to='/pojazdy'>Moje pojazdy</Vlink>
              <Vlink to='/historia'>Serwisy</Vlink>
              <NavDropdown.Divider />
              <NavDropdown.Item
                onClick={() => {
                  dispatch({ type: LOGOUT, user: null });
                }}
              >
                Wyloguj się
              </NavDropdown.Item>
            </NavDropdown>
          )}
          {/*END*/}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

const HLink = styled(Link)`
  text-decoration: none;
  padding: 5px;
  color: azure;

  &:hover {
    text-decoration: none;
    color: #666666;
  }
`;

const Vlink = styled(Link)`
  text-decoration: none;
  display: block;
  padding: 4px 24px;
  color: #212429;
  &:hover {
    text-decoration: none;
    color: #666666;
  }
`;
