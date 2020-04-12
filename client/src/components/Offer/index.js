import { Badge, Button, Card } from 'react-bootstrap';
import React from 'react';

export const OfferDiv = ({ title, photo, description, price }) => {
  return (
    <Card
      className='bg-dark text-white m-2 text-c'
      style={{ width: '320px', height: '280px' }}
    >
      <Card.Img
        src={photo}
        alt=''
        style={{ width: '320px', height: '280px', filter: 'brightness(50%)' }}
      />
      <Card.ImgOverlay>
        <Card.Title>
          {title} <Badge variant='primary'>{price} zł</Badge>
        </Card.Title>
        <Card.Text className=''>{description}</Card.Text>
        <Card.Text className='text-center'>
          <Button>Zamów</Button>
        </Card.Text>
      </Card.ImgOverlay>
    </Card>
  );
};
