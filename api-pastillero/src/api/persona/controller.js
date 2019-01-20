import { success, notFound } from '../../services/response/'
import { Persona } from '.'
import { User } from '../user/'

export const create = ({ bodymen: { body } }, res, next) => {

  let nuevaPersona = new Persona()

  nuevaPersona.nombre = body.nombre
  nuevaPersona.fecha_nacimiento = body.fecha_nacimiento
  nuevaPersona.genero = body.genero
  nuevaPersona.enfermedad = body.enfermedad

  Persona.create(nuevaPersona)
    .then((persona) => {
      return new Promise((resolve, reject) => {
        User.findByIdAndUpdate(body.user_id, { $push: {personas: persona} }, (err, user) => {
          if(err) {
            return reject(err.me)
          }

          return resolve(persona)
        })
      })
    })
  
    .then(success(res, 201))
    .catch(next)

}

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Persona.count(query)
    .then(count => Persona.find(query, select, cursor)
      .then((personas) => ({
        count,
        rows: personas.map((persona) => persona.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Persona.findById(params.id)
    .then(notFound(res))
    .then((persona) => persona ? persona.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Persona.findById(params.id)
    .then(notFound(res))
    .then((persona) => persona ? Object.assign(persona, body).save() : null)
    .then((persona) => persona ? persona.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Persona.findById(params.id)
    .then(notFound(res))
    .then((persona) => persona ? persona.remove() : null)
    .then(success(res, 204))
    .catch(next)
