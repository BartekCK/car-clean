import React, { useContext, useState } from 'react';
import { Button, Col, Image, Row } from 'react-bootstrap';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { MdAccountCircle, MdLock } from 'react-icons/md';
import styled from 'styled-components';
import Logo from '../../resources/img/logo.png';
import { ErrorModal } from '../../helpers/error';
import { getUserByToken, postLoginUser } from '../../helpers/apiCommands';
import { AuthContext } from '../../context';
import { LOGIN } from '../../context/reducer';
import { Redirect } from 'react-router-dom';

const initialState = {
  username: '',
  password: '',
  error: null,
};

export const SignIn = (props) => {
  const [credentials, setCredentials] = useState(initialState);

  const { dispatch } = useContext(AuthContext);

  const updateCredentials = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const startLogin = async () => {
    try {
      const result = await postLoginUser(credentials);
      localStorage.setItem('@token', result.data.token);
      const user = (await getUserByToken()).data;
      user.token = result.data.token;
      dispatch({ type: LOGIN, user: { ...user } });
      setCredentials({ ...credentials, error: false });
    } catch (e) {
      setCredentials({ ...credentials, error: true });
    }
  };

  return (
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
              value={credentials.username}
              name='username'
              onChange={updateCredentials}
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
              value={credentials.password}
              name='password'
              onChange={updateCredentials}
              placeholder='Hasło'
              type='password'
            />
          </InputGroup>
        </Col>
        <Col>
          <Button
            onClick={startLogin}
            className='float-right mb-3'
            variant='primary'
          >
            Zaloguj
          </Button>
        </Col>
      </Row>
      {credentials.error === false && <Redirect to='/' />}
      {credentials.error === true && (
        <ErrorModal
          text='Zły login lub hasło'
          onHide={() => setCredentials({ ...credentials, error: null })}
        />
      )}
    </CredentialsWrapper>
  );
};

export const CredentialsWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;
