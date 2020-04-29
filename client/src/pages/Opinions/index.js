import React, { useContext, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Modal } from 'react-bootstrap';
import { AuthContext } from '../../context';
import Rating from 'react-rating';
import { addOpinionByUser, getAllOpinions } from '../../helpers/apiCommands';
import { SingleOpinion } from '../../components/Opinion';

const AddOpinion = ({ show, hide }) => {
  const [opinion, setOpinion] = useState({
    mark: 1,
    description: '',
  });

  const [file, setFile] = useState(null);

  const updateFile = (e) => {
    setFile(e.target.files[0]);
  };

  const safeOpinion = async () => {
    let formData = new FormData();
    formData.append('file', file);
    formData.append(
      'createOpinionDto',
      new Blob(
        [
          JSON.stringify({
            ...opinion,
          }),
        ],
        { type: 'application/json' }
      )
    );

    try {
      await addOpinionByUser(formData);
      hide();
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <Modal show={show}>
      <Modal.Header>
        <Modal.Title>Dodaj opinię</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Rating
          placeholderRating={opinion.mark}
          onClick={(mark) => setOpinion({ ...opinion, mark })}
        />
        <Form>
          <Form.Group>
            <Form.Label>Dodaj Wiadomość</Form.Label>
            <Form.Control
              as='textarea'
              rows='3'
              onChange={(e) =>
                setOpinion({ ...opinion, description: e.target.value })
              }
            />
          </Form.Group>
        </Form>
        <input name='file' type='file' onChange={updateFile} />
      </Modal.Body>
      <Modal.Footer>
        <Button variant='danger' onClick={hide}>
          Anuluj
        </Button>
        <Button onClick={safeOpinion}>Zapisz</Button>
      </Modal.Footer>
    </Modal>
  );
};

export const Opinions = (props) => {
  const { authState } = useContext(AuthContext);

  const [opinions, setOpinions] = useState([]);

  const [isAddModalVisible, setModalVisible] = useState(false);

  useEffect(() => {
    getAllOpinions()
      .then((res) => {
          setOpinions(res);
      })
      .catch((err) => console.log(err));
  },[]);

  return (
    <Container className='shadow p-2'>
      {authState.isAuthenticated && (
        <Col className='mb-2'>
          <Button onClick={() => setModalVisible(true)}>Dodaj opinię</Button>
          <AddOpinion
            show={isAddModalVisible}
            hide={() => setModalVisible(false)}
          />
        </Col>
      )}
      <Col>
        {opinions.length > 0 &&
          opinions.map((opinion, id) => (
            <SingleOpinion
              key={id}
              username={opinion.username}
              description={opinion.description}
              date={opinion.date}
              image={opinion.image}
              mark={opinion.mark}
            />
          ))}
      </Col>
    </Container>
  );
};
