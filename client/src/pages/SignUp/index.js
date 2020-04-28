import React, { useEffect, useState } from 'react';
import { Alert, Button, Col, Row } from 'react-bootstrap';
import { CredentialsWrapper } from '../SignIn';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { MdAccountCircle, MdLock, MdMail, MdPhone } from 'react-icons/md';
import { Redirect } from 'react-router-dom';
import { ErrorModal } from '../../helpers/error';
import { postCreateUser } from '../../helpers/apiCommands';

export const MyInput = ({
  value,
  name,
  placeholder,
  SpecialIcon,
  updateCredentials,
  type = 'text',
}) => (
  <Col>
    <InputGroup className='mb-3'>
      <InputGroup.Prepend>
        <InputGroup.Text>
          <SpecialIcon />
        </InputGroup.Text>
      </InputGroup.Prepend>
      <FormControl
        value={value}
        name={name}
        onChange={updateCredentials}
        placeholder={placeholder}
        type={type}
      />
    </InputGroup>
  </Col>
);

export const SignUp = () => {
  const [data, setData] = useState({
    username: '',
    email: '',
    password: '',
    rePassword: '',
    phone: '',
  });
  const [error, setError] = useState(null);
  const [showModal, setModal] = useState(null);

  const updateCredentials = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const sendData = async () => {
    if (!error) {
      try {
        await postCreateUser(data);
        setModal(false);
      } catch (e) {
        if (e.response) {
          setError({ message: e.response.data });
          setModal(true);
        }
      }
    }
  };

  useEffect(() => {
    if (!data.username) {
      setError({ message: 'Nazwa użytkownika jest wymagana' });
    } else if (!data.email) {
      setError({ message: 'Email jest wymagany' });
    } else if (
      !/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i.test(data.email)
    ) {
      setError({ message: 'Niepoprawny email' });
    } else if (!data.password) {
      setError({ message: 'Hasło jest wymagane' });
    } else if (!data.rePassword) {
      setError({ message: 'Powtórz hasło' });
    } else if (data.password !== data.rePassword) {
      setError({ message: 'Hasła nie są identyncze' });
    } else if (!data.phone) {
      setError({ message: 'Brak numeru telefonu' });
    } else if (!/\d{9}$/i.test(data.phone)) {
      setError({ message: 'Błędny numer' });
    } else {
      setError(null);
    }
  }, [data]);

  return (
    <CredentialsWrapper>
      <Row className=' d-flex shadow flex-column p-2'>
        <Col>{error && <Alert variant='danger'>{error.message}</Alert>}</Col>
        <MyInput
          name='username'
          placeholder='Nazwa użytkownika'
          SpecialIcon={MdAccountCircle}
          value={data.username}
          updateCredentials={updateCredentials}
        />
        <MyInput
          name='email'
          placeholder='Twój email'
          SpecialIcon={MdMail}
          value={data.email}
          updateCredentials={updateCredentials}
          type='email'
        />
        <MyInput
          name='password'
          placeholder='Hasło'
          SpecialIcon={MdLock}
          value={data.password}
          updateCredentials={updateCredentials}
          type='password'
        />
        <MyInput
          name='rePassword'
          placeholder='Powtórz hasło'
          SpecialIcon={MdLock}
          value={data.rePassword}
          updateCredentials={updateCredentials}
          type='password'
        />
        <MyInput
          name='phone'
          placeholder='Numer telefonu'
          SpecialIcon={MdPhone}
          value={data.phone}
          updateCredentials={updateCredentials}
        />
        <Col>
          <Button onClick={sendData} className='float-right' disabled={error}>
            Zarejestruj
          </Button>
        </Col>
      </Row>

      {showModal === false && <Redirect to='/zaloguj' />}
      {showModal === true && (
        <ErrorModal text={error.message} onHide={() => setModal(null)} />
      )}
    </CredentialsWrapper>
  );
};
