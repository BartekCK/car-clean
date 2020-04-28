import { Button, Modal } from 'react-bootstrap';
import React from 'react';
import styled from 'styled-components';

export const ErrorModal = ({ text, onHide }) => (
  <Modal size='lg' centered show={true}>
    <Modal.Header>
      {text ? (
        <p>{text}</p>
      ) : (
        <p>Wystąpił nieoczekiwany błąd, spróbuj ponownie za chwilę</p>
      )}

      <Button variant='danger' onClick={onHide}>
        Dalej
      </Button>
    </Modal.Header>
  </Modal>
);

export const ErrorDiv = ({ message }) => (
  <ErrorWrapper>
    <h3>{message}</h3>
  </ErrorWrapper>
);

const ErrorWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  & > h3 {
    color: red;
  }
`;
