import React from "react";
import {Container} from "react-bootstrap";
import styled from "styled-components";

export class NotFound extends React.Component{


    render() {
        return(
            <NotFoundWrapper>
                <Container className='shadow text-center'>
                    <H1>Strona "{this.props.location.pathname}" nie została znaleziona</H1>
                    <p>
                        Prawdopodobnie podaleś błędny adres strony internetowej, wróc do stony głównej.
                    </p>
                </Container>
            </NotFoundWrapper>
        )
    }
}

const NotFoundWrapper = styled.div`
display: flex;
width: 100vw;
height: 100vh;
justify-content: center;
align-items: center;
`

const H1 = styled.h1`
color: red;
`
