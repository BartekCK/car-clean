import Payments24 from '../../resources/img/add/przelewy24.png';
import MastercardImage from '../../resources/img/add/mastercard.png';
import VisaImage from '../../resources/img/add/visa.png';
import { Button, FormControl, InputGroup } from 'react-bootstrap';
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

export const AddImage = styled.img`
  max-width: 170px;
  max-height: 90px;
`;

const AddContainer = styled.div`
  display: inline-block;
  text-align: center;
`;
