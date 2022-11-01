
class myFooter extends HTMLElement{

    connectedCallback(){
        //<my-Footer> </my-Footer>
        this.innerHTML =`
                    
<!-- External footer Code -->



             `

    }
}

customElements.define('my-footer', myFooter)