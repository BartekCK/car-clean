import React from 'react';
import { Image, Nav, Navbar, NavDropdown } from 'react-bootstrap';
import Logo from './../../resources/img/logo.png';
import User from './../../resources/img/user.png';

const UserImage = () => <img alt='' width='40' height='40' src={User} />;

export const AppNav = () => (
  <Navbar
    className='border-bottom border-dark'
    collapseOnSelect
    expand='lg'
    bg='dark'
    variant='dark'
  >
    <Navbar.Brand href='/'>
      <img
        alt=''
        src={Logo}
        width='100'
        height='40'
        className='d-inline-block align-top'
      />
    </Navbar.Brand>
    <Navbar.Toggle aria-controls='responsive-navbar-nav' />
    <Navbar.Collapse id='responsive-navbar-nav'>
      <Nav className='mr-auto'>
        <Nav.Link href='/sklep'>Sklep</Nav.Link>
        <Nav.Link href='/oferta'>Oferta</Nav.Link>
        <Nav.Link href='/kontakt'>Kontakt</Nav.Link>
      </Nav>
      <Nav className='mr-1'>
        {/*IF USER NO SIGN IN */}
        <Nav.Link href='/zaloguj'>Zaloguj</Nav.Link>
        <Nav.Link eventKey={2} href='/zarejestruj'>
          Zarejestruj
        </Nav.Link>
        {/*END*/}
        {/*IF USER IS SIGN IN*/}
        <NavDropdown
          as={Image}
          title={<UserImage />}
          id='dropdown-basic'
          drop='left'
        >
          <NavDropdown.Item href='/koszyk'>Koszyk</NavDropdown.Item>
          <NavDropdown.Divider />
          <NavDropdown.Item href='/wylogowanie'>Wyloguj się</NavDropdown.Item>
        </NavDropdown>
        {/*END*/}
      </Nav>
    </Navbar.Collapse>
  </Navbar>
);
