import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
import { token } from '../../services/passport'
export Tratamiento, { schema } from './model'

const router = new Router()
const { nombreTratamiento,periodo_toma, diasDuracionTratamiento } = schema.tree

/**
 * @api {post} /tratamientos Create tratamiento
 * @apiName CreateTratamiento
 * @apiGroup Tratamiento
 * @apiParam periodo_toma Tratamiento's periodo_toma.
 * @apiParam fecha_inicio Tratamiento's fecha_inicio.
 * @apiParam fecha_final Tratamiento's fecha_final.
 * @apiSuccess {Object} tratamiento Tratamiento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Tratamiento not found.
 */
router.post('/',
  token({required: true, roles:['admin','user']}),
  body({ nombreTratamiento,periodo_toma, diasDuracionTratamiento }),
  create)

/**
 * @api {get} /tratamientos Retrieve tratamientos
 * @apiName RetrieveTratamientos
 * @apiGroup Tratamiento
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of tratamientos.
 * @apiSuccess {Object[]} rows List of tratamientos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  token({required: true, roles:['admin','user']}),
  query(),
  index)

/**
 * @api {get} /tratamientos/:id Retrieve tratamiento
 * @apiName RetrieveTratamiento
 * @apiGroup Tratamiento
 * @apiSuccess {Object} tratamiento Tratamiento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Tratamiento not found.
 */
router.get('/:id',
  token({required: true, roles:['admin','user']}),
  show)

/**
 * @api {put} /tratamientos/:id Update tratamiento
 * @apiName UpdateTratamiento
 * @apiGroup Tratamiento
 * @apiParam periodo_toma Tratamiento's periodo_toma.
 * @apiParam fecha_inicio Tratamiento's fecha_inicio.
 * @apiParam fecha_final Tratamiento's fecha_final.
 * @apiSuccess {Object} tratamiento Tratamiento's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Tratamiento not found.
 */
router.put('/:id',
  token({required: true, roles:['admin','user']}),
  body({ nombreTratamiento,periodo_toma, diasDuracionTratamiento }),
  update)

/**
 * @api {delete} /tratamientos/:id Delete tratamiento
 * @apiName DeleteTratamiento
 * @apiGroup Tratamiento
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Tratamiento not found.
 */
router.delete('/:id',
  token({required: true, roles:['admin','user']}),
  destroy)

export default router
