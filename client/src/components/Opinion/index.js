import { Card, Image, Row } from 'react-bootstrap';
import React from 'react';
import Rating from 'react-rating';
import styled from 'styled-components';
import { formatDate } from '../../helpers/time';
export const SingleOpinion = ({ username, mark, date, description, image }) => (
  <Row className='d-flex align-items-center justify-content-center my-3'>
    <Image rounded src={image} style={{ width: '320px', height: '280px' }}/>
    <Card style={{ width: '320px', height: '280px' }}>
      <Card.Header className='d-flex align-items-center justify-content-center'>
        <Span>Dodał: {username}</Span>
        <Rating
          placeholderRating={mark}
          readonly={true}
        />
      </Card.Header>
      <Card.Body>
        <Card.Text className='overflow-auto'>{description}</Card.Text>
      </Card.Body>
      <Card.Footer>
        <Span>Data Dodania: {formatDate(date)}</Span>
      </Card.Footer>
    </Card>
  </Row>
);

const Span = styled.span`
font-weight: bold;
margin: 0 5px 0 0;
`
