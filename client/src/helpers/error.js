import {Button, Modal} from "react-bootstrap";
import React from "react";


export const ErrorModal = ({ onHide }) => (
    <Modal size='lg' centered show={true}>
        <Modal.Header>
            <p>Wystąpił nieoczekiwany błąd, spróbuj ponownie za chwilę</p>
            <Button variant='danger' onClick={onHide}>
                Dalej
            </Button>
        </Modal.Header>
    </Modal>
);
