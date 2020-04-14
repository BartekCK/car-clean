import {Card} from 'react-bootstrap';
import React from 'react';

export const SingleOpinion = ({ id, name ,mark ,date, content, imgc1, imgc2, imgc3 }) => (
    <Card style={{ width: '68rem', marginTop: '10px' }}>
{/*        <Card.Img variant="top" src={imgc1} />
        <Card.Img variant="top" src={imgc2} />
        <Card.Img variant="top" src={imgc3} />*/}
        <Card.Header>{name}<br/><p>wystawiona ocena: {mark} </p></Card.Header>
        <Card.Body>
            <Card.Text>
                {content}
            </Card.Text>
        </Card.Body>
        <Card.Footer>
            <p>Dodano: {date}</p>
        </Card.Footer>
    </Card>
);
