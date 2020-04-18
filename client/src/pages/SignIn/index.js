import React from 'react';
import { Button, Col, Image, Row } from 'react-bootstrap';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { MdAccountCircle, MdLock } from 'react-icons/md';
import styled from 'styled-components';
import Logo from '../../resources/img/logo.png';
import { ErrorModal } from '../../helpers/error';
import { Redirect } from 'react-router-dom';
import { AuthContextConsumer } from '../../context';

export class SignIn extends React.Component {
  state = {
    username: '',
    password: '',
  };

  updateCredentials = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  render() {
    const { username, password } = this.state;
    return (
      <AuthContextConsumer>
        {({ login, error, resetError }) => (
          <CredentialsWrapper>
            <Row className=' d-flex shadow flex-column'>
              <Col className='text-center mt-3'>
                <Image src={Logo} />
              </Col>
              <Col>
                <InputGroup className='my-3'>
                  <InputGroup.Prepend>
                    <InputGroup.Text>
                      <MdAccountCircle />
                    </InputGroup.Text>
                  </InputGroup.Prepend>
                  <FormControl
                    value={username}
                    name='username'
                    onChange={this.updateCredentials}
                    placeholder='Nazwa użytkownika'
                  />
                </InputGroup>
              </Col>
              <Col>
                <InputGroup className='mb-3'>
                  <InputGroup.Prepend>
                    <InputGroup.Text>
                      <MdLock />
                    </InputGroup.Text>
                  </InputGroup.Prepend>
                  <FormControl
                    value={password}
                    name='password'
                    onChange={this.updateCredentials}
                    placeholder='Hasło'
                    type='password'
                  />
                </InputGroup>
              </Col>
              <Col>
                <Button
                  onClick={() => login(this.state)}
                  className='float-right mb-3'
                  variant='primary'
                >
                  Zaloguj
                </Button>
              </Col>
            </Row>
            {error === false && <Redirect to='/' />}
            {error === true && <ErrorModal onHide={resetError} />}
          </CredentialsWrapper>
        )}
      </AuthContextConsumer>
    );
  }
}

export const CredentialsWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;
