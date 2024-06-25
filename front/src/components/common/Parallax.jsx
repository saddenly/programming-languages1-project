import React from "react"
import { Container } from "react-bootstrap"

const Parallax = () => {
	return (
		<div className="parallax mb-5 d-flex align-items-center justify-content-center">
			<Container className="text-center px-5 py-5">
				<div className="animated-texts bounceIn">
					<h1>
						<span className="hotel-color">Experience the Best hospitality at Project Hotel</span>
					</h1>
					<h3>We offer the best services for all your needs.</h3>
				</div>
			</Container>
		</div>
	)
}


export default Parallax
