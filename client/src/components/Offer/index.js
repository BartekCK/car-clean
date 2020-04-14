import {Badge, Button, Card, Col} from 'react-bootstrap';
import React from 'react';
import {Link} from 'react-router-dom';

export const OfferDiv = ({
  id,
  title,
  photo,
  description,
  price,
  disable = false,
}) => {
  return (
    <Col>
      <Card
        className='bg-dark text-white my-2 mx-auto'
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
            {!disable && (
              <Link to={`/oferta/${id}`}>
                <Button>Zamów</Button>
              </Link>
            )}
          </Card.Text>
        </Card.ImgOverlay>
      </Card>
    </Col>
  );
};
