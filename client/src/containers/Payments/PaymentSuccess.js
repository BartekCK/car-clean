import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { Container, Spinner } from 'react-bootstrap';
import { acceptPay } from '../../helpers/apiCommands';
import styled from 'styled-components';

export const PaymentSuccess = () => {
  const location = useLocation();

  const [message, setMessage] = useState('');

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const paymentId = searchParams.get('paymentId');
    const PayerID = searchParams.get('PayerID');

    acceptPay(paymentId, PayerID)
      .then((res) => {
        setMessage(res.data);
      })
      .catch((res) => {
        console.log(res);
        if(res.response)
          setMessage(res.response.data);
      });
  }, [location]);

  return (
    <Wrapper>
      <Container className='shadow d-flex align-items-center justify-content-center w-50 h-50'>
        {message.length > 0 ? (
          <H1>{message}</H1>
        ) : (
          <div style={{ textAlign: 'center' }}>
            <p>Nie opuszczaj strony</p>
            <Spinner
              animation='border'
              role='status'
              variant='primary'
              size='xl'
            >
              <span className='sr-only'>Loading...</span>
            </Spinner>
          </div>
        )}
      </Container>
    </Wrapper>
  );
};
const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const H1 = styled.h1`
  color: green;
  font-weight: bold;
  font-size: 2em;
`;
