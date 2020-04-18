import React from 'react';
import {
  Container,
  Accordion,
  Card,
  Button,
  Form,
  Col,
  Modal,
  FormControl,
} from 'react-bootstrap';
import { SingleOpinion } from '../../components/Opinion';
import { ErrorModal } from '../../helpers/error';

export class Opinions extends React.Component {
  state = {
    topinions: [
      //TEMP DATA INSIDE
      {
        id: 0,
        username: 'Michal',
        mark: 4,
        date: '2020-03-02',
        text_content:
          "dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
        img_content1: '../../resources/img/opinions/1.jpg',
        img_content2: '../../resources/img/opinions/2.jpg',
        img_content3: '../../resources/img/opinions/3.jpg',
      },
      {
        id: 1,
        username: 'Aleksander',
        mark: 2,
        date: '2019-02-05',
        text_content:
          'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.',
        img_content1: '../../resources/img/opinions/4.jpg',
        img_content2: '../../resources/img/opinions/5.jpg',
        img_content3: '../../resources/img/opinions/6.jpg',
      },
      {
        id: 2,
        username: 'Katarzyna',
        mark: 5,
        date: '2020-02-12',
        text_content:
          "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.",
        img_content1: '../../resources/img/opinions/7.jpg',
        img_content2: '../../resources/img/opinions/8.jpg',
        img_content3: '../../resources/img/opinions/9.jpg',
      },
      {
        id: 3,
        username: 'Gargamel',
        mark: 5,
        date: '2020-01-13',
        text_content:
          'The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33',
        img_content1: '../../resources/img/opinions/10.jpg',
        img_content2: '../../resources/img/opinions/11.jpg',
        img_content3: '../../resources/img/opinions/12.jpg',
      },
      {
        id: 4,
        username: 'Marcin',
        mark: 1,
        date: '2019-05-02',
        text_content:
          'Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free',
        img_content1: '../../resources/img/opinions/13.jpg',
        img_content2: '../../resources/img/opinions/14.jpg',
        img_content3: '../../resources/img/opinions/15.jpg',
      },
    ],
    formcontent: '',
    errormodal: null,
  };

  updateCredentials = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  addOpinion = () => {
    const { formcontent, errormodal } = this.state;
    if (formcontent.length > 0) {
      //POST TO API WITH DATA LOCATED IN STATE
      console.log(formcontent);
      console.log(errormodal);
      this.setState({ errormodal: true }); //IF GOOD
      // this.setState({ showModal: false }); //IF WRONG
    } else {
      this.setState({ errormodal: false });
    }
  };

  componentDidMount = () => {
    // GET FROM API OPINIONS WITH NAME OF USER
  };
  render() {
    const { topinions, formcontent, errormodal } = this.state;
    return (
      <Container className='mt-4'>
        {/*BUTTON AVAILABLE ONLY FOR LOGGED USER*/}

        <Accordion defaultActiveKey='1'>
          <Card>
            <Card.Header>
              <Accordion.Toggle as={Button} variant='link' eventKey='0'>
                Dodaj swoją opinię!
              </Accordion.Toggle>
            </Card.Header>
            <Accordion.Collapse eventKey='0'>
              <Card.Body>
                <Form>
                  <Form.Group>
                    <Form.Label>Treść</Form.Label>
                    <FormControl
                      value={formcontent}
                      name='formcontent'
                      onChange={this.updateCredentials}
                      placeholder='opinia'
                    />
                    <br />
                    <Form.Label>Wystaw ocenę</Form.Label>
                    <Form.Control as='select' size='sm' custom>
                      <option>1</option>
                      <option>2</option>
                      <option>3</option>
                      <option>4</option>
                      <option>5</option>
                    </Form.Control>
                  </Form.Group>
                  <div className='mb-3'>
                    <Form.File id='formcheck-api-regular'>
                      <Form.File.Label>Dodaj zdjęcia do opinii</Form.File.Label>
                      <Form.File.Input />
                      <Form.File.Input />
                      <Form.File.Input />
                    </Form.File>
                  </div>
                  <Button
                    onClick={this.addOpinion}
                    variant='primary'
                    type='submit'
                  >
                    Wyślij
                  </Button>
                </Form>
              </Card.Body>
            </Accordion.Collapse>
          </Card>
        </Accordion>
        <Col className='p-3 d-flex flex-column justify-content-center align-items-center'>
          {topinions.length > 0 ? (
            topinions.map((opinion) => (
              <SingleOpinion
                key={opinion.id}
                id={opinion.id}
                name={opinion.username}
                mark={opinion.mark}
                date={opinion.date}
                content={opinion.text_content}
                imgc1={opinion.img_content1}
                imgc2={opinion.img_content2}
                imgc3={opinion.img_content3}
              />
            ))
          ) : (
            <>
              <h3>Nie ma tu jeszcze opini</h3>
            </>
          )}
        </Col>
        {errormodal === true && (
          <AddOpinionModal onHide={() => this.setState({ errormodal: null })} />
        )}
        {errormodal === false && (
          <ErrorModal onHide={() => this.setState({ errormodal: null })} />
        )}
      </Container>
    );
  }
}

export const AddOpinionModal = ({ onHide }) => (
  <Modal size='lg' centered show={true}>
    <Modal.Header>
      <p>Pomyślnie dodano opinie</p>
      <Button variant='success' onClick={onHide}>
        Dalej
      </Button>
    </Modal.Header>
  </Modal>
);
