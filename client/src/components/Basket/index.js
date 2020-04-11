import Payments24 from '../../resources/img/add/przelewy24.png';
import MastercardImage from '../../resources/img/add/mastercard.png';
import VisaImage from '../../resources/img/add/visa.png';
import {
  Accordion,
  Button,
  Card,
  FormControl,
  InputGroup,
} from 'react-bootstrap';
import React from 'react';
import styled from 'styled-components';

export const AddDiv = () => (
  <AddContainer>
    <AddImage alt='' src={Payments24} />
    <AddImage alt='' src={MastercardImage} />
    <AddImage alt='' src={VisaImage} />
  </AddContainer>
);

export const SingleInputProduct = ({ id, name, price, deleteProd }) => (
  <InputGroup className='my-1'>
    <FormControl disabled={true} placeholder={name + ' ' + price + ' zł'} />
    <Button onClick={() => deleteProd(id)} variant='danger'>
      -
    </Button>
  </InputGroup>
);

export const PaymentButton = () => (
  <Accordion className='m-1 align-self-stretch'>
    <Card className='justify-content-between'>
      <Accordion.Toggle as={Button} variant='primary' eventKey='0'>
        Kup teraz
      </Accordion.Toggle>
      <Accordion.Collapse eventKey='0'>
        <Card.Body className='d-flex justify-content-between'>
          <Button className='mr-1' variant='outline-primary'>
            Płatność przelewem
          </Button>
          <Button variant='outline-success'>Płatność przy odbiorze</Button>
        </Card.Body>
      </Accordion.Collapse>
    </Card>
  </Accordion>
);

export const AddImage = styled.img`
  max-width: 170px;
  max-height: 90px;
`;

const AddContainer = styled.div`
  display: inline-block;
  text-align: center;
`;
